package com.laozhang.max_feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    private FeignService feignService;

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/feign")
    public String feign() {
        return feignService.add(1,2);
    }
}
