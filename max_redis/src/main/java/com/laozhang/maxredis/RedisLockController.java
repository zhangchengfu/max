package com.laozhang.maxredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 利用redis获取分布式所
 */
@Controller
public class RedisLockController {

    @Autowired
    private RedisLock redisLock;

    @ResponseBody
    @RequestMapping("test")
    public Object test() {
        try {
            // 获取分布式所
            redisLock.lock("test");

            // ......
        } finally {
            // 释放所
            redisLock.unlock("test");
        }
        return null;
    }
}
