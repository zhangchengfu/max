package com.laozhang.max.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MaxServiceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxServiceAApplication.class, args);
    }
}
