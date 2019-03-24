package com.laozhang.max_oauth2_server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages= {"com.laozhang.max_oauth2_server.mapper"})
public class MybtaisConfig {

}
