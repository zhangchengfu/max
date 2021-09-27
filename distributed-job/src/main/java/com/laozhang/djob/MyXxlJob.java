package com.laozhang.djob;


import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyXxlJob {

    @XxlJob("myXxlJobHandler")
    public ReturnT<String> execute(String param) {

        log.info("myXxlJobHandler execute...");
        XxlJobLogger.log("myXxlJobHandler execute...");
        return ReturnT.SUCCESS;
    }
}
