package com.laozhang.max_mybatis_mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.laozhang.max_mybatis_mapper.mapper")
public class MaxMybatisMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxMybatisMapperApplication.class, args);
    }

}
