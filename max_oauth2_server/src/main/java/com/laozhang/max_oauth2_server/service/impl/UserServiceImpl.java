package com.laozhang.max_oauth2_server.service.impl;

import com.laozhang.max_oauth2_server.entity.User;
import com.laozhang.max_oauth2_server.mapper.UserMapper;
import com.laozhang.max_oauth2_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUserName(username);
    }
}
