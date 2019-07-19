package com.laozhang.maxserviceb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon")
    public Object ribbon() {
        String info = restTemplate.getForObject("http://service-A/add?a=1&b=2", String.class);
        return info;
    }
}
