package com.laozhang.max_oauth2_client.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GloableController {

    //三方登陆系统地址
    @Value("${oauth2.authorizeUrl}")
    private String loginUrl;

    //oauth2服务器响应类型
    private String responseType = "code";

    //客户端id
    @Value("${oauth2.clientId}")
    private String clientId;

    //服务器端登录成功/失败后重定向到的客户端地址
    @Value("${oauth2.redirectUrl}")
    private String redirectUrl;

    @RequestMapping("/")
    public String index(Model model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "oauth";
        }
        model.addAttribute("oauth_url", oauthUrl());
        return "index";
    }

    @RequestMapping("/oauth2Failure")
    public String fail() {
        return "index";
    }

    @RequestMapping("/oauth2-success")
    public String oauth() {
        return "oauth";
    }

    @RequestMapping("/logout")
    public Object logout() {
        SecurityUtils.getSubject().logout();
        return "index";
    }

    private String oauthUrl() {
        return loginUrl + "?response_type=" + responseType + "&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
    }
}
