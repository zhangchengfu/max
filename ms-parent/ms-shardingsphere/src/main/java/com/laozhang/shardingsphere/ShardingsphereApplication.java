package com.laozhang.shardingsphere;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.stereotype.Component;

@Component("com.laozhang.shardingsphere")
@MapperScan(basePackages = "com.laozhang.shardingsphere.repository")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})
public class ShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereApplication.class, args);
    }
}
