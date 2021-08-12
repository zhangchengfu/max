package com.laozhang.maxredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock2 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String LOCK_SUFFIX = "_lock";

    /**
     * 分布式所
     *
     * @param key
     */
    public void lock(String key) {
        boolean lock;
        while (true) {
            lock = redisTemplate.opsForValue().setIfAbsent(key + LOCK_SUFFIX, "");
            if (lock) {
                // 设置分布式锁最长时间为10秒，超时自动去除，防止死锁的情况发生
                redisTemplate.expire(key + LOCK_SUFFIX, 10, TimeUnit.SECONDS);
                logger.info("setting expire 10 seconds");
                break;
            }
        }
    }

    /**
     * 解除分布式锁
     *
     * @param key
     */
    public void unlock(String key) {
        redisTemplate.delete(key + LOCK_SUFFIX);
    }

}
