package com.laozhang.max_oauth2_server.entity;

import lombok.Data;

import java.io.Serializable;

//@Data
public class User implements Serializable {

    private Long id;
    private String username; //用户名
    private String password; //密码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
