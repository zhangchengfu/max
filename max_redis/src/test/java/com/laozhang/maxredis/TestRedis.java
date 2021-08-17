package com.laozhang.maxredis;

import com.laozhang.maxredis.constants.RedisKeyConstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DefaultRedisScript defaultRedisScript;

    // 分布式锁获取、解锁
    @Test
    public void testRedission() throws Exception {

        RLock lock = redissonClient.getLock("abc");
        try {
            boolean isLocked = lock.tryLock(10L, TimeUnit.SECONDS);
            if (isLocked) {
                System.out.println("获取到锁");

                // redis扣库存
                // hset stock amount 2
                // hset stock amount
                List<String> keys = new ArrayList<>();
                keys.add("stock");
                keys.add("amount");
                Long amount = (Long) redisTemplate.execute(defaultRedisScript, keys);
                System.out.println("amout:" + amount);
            }
        } finally {
            if (lock.isLocked()) {
                System.out.println("解锁");
                lock.unlock();
            }
        }
    }

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObject() throws Exception {
        User user = new User("laozhang", "123456");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("user1", user);
        operations.set("user2", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("user2");
        boolean exists = redisTemplate.hasKey("user2");
        if (exists) {
            System.out.println("exists is true");
        } else {
            System.out.println("exists is false");
        }
    }

    /**
     * 操作集合
     *
     * 添加数据
     * sadd user:1 1 2 3 4
     * smembers user:1
     * sadd user:2 3 4 5 6
     * smembers user:2
     * sinter user:1 user:2
     *
     */
    @Test
    public void handleSet() {

        // 增加
        redisTemplate.opsForSet().add("user:1", 7);
        // 取交集
        Set<Integer> dinerIds = redisTemplate.opsForSet().intersect("user:1", "user:2");
        // 删除
        redisTemplate.opsForSet().remove("user:1", 7);
        System.out.println(dinerIds);

    }

    /**
     * 增加有序集合
     */
    @Test
    public void addZset() {

        for (int i = 0; i < 40; i++) {
            redisTemplate.opsForZSet().incrementScore(
                    RedisKeyConstant.diner_points.getKey(), "user"+i, new Random().nextInt(100));
        }
    }

    /**
     * Top 20
     */
    @Test
    public void top20() {

        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(
                RedisKeyConstant.diner_points.getKey(), 0, 19);
        int rank = 1;
        for (ZSetOperations.TypedTuple<String> rangeWithScore : rangeWithScores) {
            // iD
            String id = rangeWithScore.getValue();
            // 积分
            int points = rangeWithScore.getScore().intValue();
            System.out.println("排名：" + (rank++) + "\t" + "id:" + id + "\t" + "积分:" + points);
        }

        // 获取排名
        Long myRank = redisTemplate.opsForZSet().reverseRank(
                RedisKeyConstant.diner_points.getKey(), "user27");
        System.out.println("排名：" + (myRank + 1));
    }

    // BitMaps
    // 业务场景类似于签到
    /**
     * 签到
     */
    @Test
    public void sign() {

        String signKey = "user:sign:5:202108";
        // 查看是否已签到
        //boolean isSigned = redisTemplate.opsForValue().getBit(signKey, offset);
        for (int i = 0; i < 31; i++) {
            // 签到
            if (i % 3 == 0)
                redisTemplate.opsForValue().setBit(signKey, i, true);
        }
    }

    /**
     * 统计签到次数
     */
    @Test
    public void getSignCount() {

        Long count = (Long) redisTemplate.execute(
                (RedisCallback<Long>) con -> con.bitCount("user:sign:5:202108".getBytes()));
        System.out.println("count:" + count);

        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(31))
                .valueAt(0);
        List<Long> list = redisTemplate.opsForValue().bitField("user:sign:5:202108", bitFieldSubCommands);
        long v = list.get(0) == null ? 0 : list.get(0);
        int signCount = 0;
        for (int i = 31; i > 0; i--) {// i 表示位移操作次数
            // 右移再左移，如果等于自己说明最低位是 0，表示未签到
            if (v >> 1 << 1 == v) {

            } else {
                System.out.println("天数：" + i + "签到");
                signCount++;
            }
            // 右移一位并重新赋值，相当于把最低位丢弃一位
            v >>= 1;
        }
        System.out.println("signcount:" + signCount);
    }

    // 经纬度
    /**
     * 更新经纬度
     */
    @Test
    public void updateLocation() {

        // 获取 key diner:location
        String key = RedisKeyConstant.diner_location.getKey();
        // 将用户地理位置信息存入 Redis
        RedisGeoCommands.GeoLocation geoLocation = new RedisGeoCommands
                .GeoLocation("zhangsan", new Point(121.446617, 31.205593));
        redisTemplate.opsForGeo().add(key, geoLocation);
    }

    /**
     * 查询最近的人
     */
    @Test
    public void findNearMe() {

        // 如果经纬度没传，那么从 Redis 中获取
        List<Point> points = redisTemplate.opsForGeo().position(RedisKeyConstant.diner_location.getKey(), "zhangsan");

        // 初始化距离对象，单位 m
        Distance distance = new Distance(1000,
                RedisGeoCommands.DistanceUnit.METERS);
        // 初始化 Geo 命令参数对象
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
        // 附近的人限制 20，包含距离，按由近到远排序
        args.limit(20).includeDistance().sortAscending();
        // 以用户经纬度为圆心，范围 1000m
        Point point = new Point(121.446617, 30.205593);
        Circle circle = new Circle(point, distance);
        // 获取附近的人 GeoLocation 信息
        GeoResults<RedisGeoCommands.GeoLocation> geoResult =
                redisTemplate.opsForGeo().radius(RedisKeyConstant.diner_location.getKey(), circle, args);
    }
}
