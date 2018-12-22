package com.laozhang.maxjpa.service;

import com.laozhang.maxjpa.entity.User;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User findById(long id);

    void save(User user);

    void edit(User user);

    void delete(long id);
}
