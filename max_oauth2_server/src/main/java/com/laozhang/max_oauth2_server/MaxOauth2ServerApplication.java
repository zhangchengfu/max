package com.laozhang.max_oauth2_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MaxOauth2ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MaxOauth2ServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MaxOauth2ServerApplication.class);
    }
}
