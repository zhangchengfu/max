package com.laozhang.max_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MaxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxServerApplication.class, args);
        //new SpringApplicationBuilderlder(MaxServerApplication.class).web(true).run(args);
    }
}
