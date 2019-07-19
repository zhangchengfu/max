package com.laozhang.maxredis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

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
}
