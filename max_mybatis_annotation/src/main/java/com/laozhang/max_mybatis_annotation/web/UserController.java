package com.laozhang.max_mybatis_annotation.web;


import com.laozhang.max_mybatis_annotation.entity.UserEntity;
import com.laozhang.max_mybatis_annotation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getUsers")
    public List<UserEntity> getUsers() {
        List<UserEntity> users = userMapper.getAll();
        return users;
    }

    @GetMapping("/getUser")
    public UserEntity getUser(Long id) {
        UserEntity user = userMapper.getOne(id);
        return user;
    }

    @PostMapping("/add")
    public Long save(@RequestBody UserEntity user) {
        Long id = userMapper.insert(user);
        return id;
    }

    @PutMapping("/update")
    public void update(@RequestBody UserEntity user) {
        userMapper.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        userMapper.delete(id);
    }
}
