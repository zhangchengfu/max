package com.laozhang.maxredis;

import com.laozhang.maxredis.constants.RedisKeyConstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
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

}
