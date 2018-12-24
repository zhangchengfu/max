package com.laozhang.max_mybatis_xml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.laozhang.max_mybatis_xml.mapper")
public class MaxMybatisXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxMybatisXmlApplication.class, args);
    }

}

