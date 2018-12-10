package com.laozhang.maxmongo.dao;

import com.laozhang.maxmongo.entity.UserEntity;

public interface UserDao {
    void saveUser(UserEntity user);

    UserEntity findUserByUserName(String userName);

    int updateUser(UserEntity user);

    void deleteUserById(Long id);
}
