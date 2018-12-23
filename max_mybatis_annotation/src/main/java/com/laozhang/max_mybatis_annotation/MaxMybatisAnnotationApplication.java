package com.laozhang.max_mybatis_annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.laozhang.max_mybatis_annotation.mapper")
public class MaxMybatisAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxMybatisAnnotationApplication.class, args);
    }

}

