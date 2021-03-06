package com.laozhang.max_mybatis_xml.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laozhang.max_mybatis_xml.entity.SysUser;
import com.laozhang.max_mybatis_xml.entity.SysUserCriteria;
import com.laozhang.max_mybatis_xml.entity.UserEntity;
import com.laozhang.max_mybatis_xml.mapper.SysUserMapper;
import com.laozhang.max_mybatis_xml.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

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
    public void save(UserEntity user) {
        userMapper.insert(user);
    }

    @PutMapping(value = "update")
    public void update(UserEntity user) {
        userMapper.update(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userMapper.delete(id);
    }

    /**
     * 分页
     *
     * @return
     */
    @GetMapping("/page")
    @ResponseBody
    public PageInfo page() {
        int page = 0;
        int pageSize = 10;
        PageHelper.startPage(page, pageSize);
        SysUserCriteria criteria = new SysUserCriteria();
        List<SysUser> list = sysUserMapper.selectByExample(criteria);
        PageInfo<SysUser> info = new PageInfo<>(list);
        return info;
    }
}
