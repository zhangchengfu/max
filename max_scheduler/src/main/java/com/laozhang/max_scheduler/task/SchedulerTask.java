package com.laozhang.max_scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
    private volatile int count = 0;

    @Scheduled(cron = "*/6 * * * * ?")
    public void process() {
        System.out.println("This is scheduler task runing " + (count++));
    }
}
