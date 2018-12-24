package com.laozhang.max_mybatis_xml.mapper;

import com.laozhang.max_mybatis_xml.entity.UserEntity;

import java.util.List;

public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
