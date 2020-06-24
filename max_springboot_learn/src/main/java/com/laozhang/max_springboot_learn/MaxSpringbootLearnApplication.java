package com.laozhang.max_springboot_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class MaxSpringbootLearnApplication {

    public static void main(String[] args) {

        SpringApplication.run(MaxSpringbootLearnApplication.class, args);
    }

}
