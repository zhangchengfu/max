package com.laozhang.security;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession
@SpringBootApplication
public class OAuth2AuthServer {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthServer.class, args);
    }
}
