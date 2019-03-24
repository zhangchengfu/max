package com.laozhang.max_oauth2_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GloableController {

    @RequestMapping("/oauth2Failure")
    public String fail(){
        return "index";
    }

    @RequestMapping("/oauth2-login")
    public String oauth(){
        return "oauth";
    }
}
