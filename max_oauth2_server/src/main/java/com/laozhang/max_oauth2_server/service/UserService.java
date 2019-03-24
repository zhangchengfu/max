package com.laozhang.max_oauth2_server.service;


import com.laozhang.max_oauth2_server.entity.User;

public interface UserService {
    User findByUsername(String username);
}
