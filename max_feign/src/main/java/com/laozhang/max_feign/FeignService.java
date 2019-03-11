package com.laozhang.max_feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-A")
public interface FeignService {
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
