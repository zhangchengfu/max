package com.laozhang.max_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.laozhang.max_shiro.dao")
public class MaxShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxShiroApplication.class, args);
    }

}
