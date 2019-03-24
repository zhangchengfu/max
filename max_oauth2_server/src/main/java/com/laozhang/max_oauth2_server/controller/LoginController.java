package com.laozhang.max_oauth2_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController{

    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping(value = "/login")
    public String LoginView() {
        return "login";
    }
}
