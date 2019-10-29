package com.laozhang.max_feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-a")
public interface FeignService {
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    String add(@RequestParam("a") Integer a, @RequestParam("b") Integer b);
}
