package com.laozhang.max_interview.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logger")
public class TestLoggerController {

    public static final Logger logger = LoggerFactory.getLogger(TestLoggerController.class);

    @RequestMapping("/exportLog")
    public ResponseEntity exportLog() {
        logger.info("****************start****************");
        logger.error("****************end****************");
        return ResponseEntity.ok("success");
    }
}
