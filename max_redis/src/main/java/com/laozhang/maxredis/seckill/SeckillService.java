package com.laozhang.maxredis.seckill;

import com.laozhang.maxredis.constants.RedisKeyConstant;
import com.laozhang.maxredis.exception.ParameterException;
import com.laozhang.maxredis.utils.AssertUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DefaultRedisScript defaultRedisScript;
    @Resource
    private RedisLock redisLock;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 抢购代金券
     *
     * @param voucherId   代金券 ID
     * @param accessToken 登录token
     * @Para path 访问路径
     */
    @Transactional(rollbackFor = Exception.class)
    public Object doSeckill(Integer voucherId, String accessToken, String path) {

        // 使用 Redis 锁一个账号只能购买一次
        String lockName = "test:" + voucherId;
        long expireTime = 10000L;

        String key = RedisKeyConstant.seckill_vouchers.getKey() + voucherId;

        // 自定义 Redis 分布式锁
        //String lockKey = redisLock.tryLock(lockName, expireTime);

        // Redisson 分布式锁
        RLock lock = redissonClient.getLock(lockName);

        try {
            // 不为空意味着拿到锁了，执行下单
            // 自定义 Redis 分布式锁处理
            // if (StrUtil.isNotBlank(lockKey)) {

            // Redisson 分布式锁处理
            boolean isLocked = lock.tryLock(expireTime, TimeUnit.MILLISECONDS);
            if (isLocked) {
                // 下单

                // 采用 Redis
                // 扣库存
                // count = redisTemplate.opsForHash().increment(key, "amount", -1);
                // AssertUtil.isTrue(count < 0, "该券已经卖完了");

                // 采用 Redis + Lua 解决问题
                // 扣库存
                List<String> keys = new ArrayList<>();
                keys.add(key);
                keys.add("amount");
                Long amount = (Long) redisTemplate.execute(defaultRedisScript, keys);
                AssertUtil.isTrue(amount == null || amount < 1, "该券已经卖完了");
            }
        } catch (Exception e) {

            // 手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 自定义 Redis 解锁
            // redisLock.unlock(lockName, lockKey);

            // Redisson 解锁
            lock.unlock();
            if (e instanceof ParameterException) {
                System.out.println("该券已经卖完了");
            }
        }
        return null;
    }

}
