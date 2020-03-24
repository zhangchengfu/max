package com.laozhang.maxweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        return "index.html";
    }

    @RequestMapping("/websocket")
    public String websocket(ModelMap map) {
        return "websocket_client.html";
    }

    @RequestMapping("/test")
    @ResponseBody
    public Object test() {
        return  "success  test";
    }
}
