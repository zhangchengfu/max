package com.laozhang.max_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaxSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxSchedulerApplication.class, args);
    }

}

