package com.laozhang.maxgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class MaxGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxGatewayApplication.class, args);
    }
}
