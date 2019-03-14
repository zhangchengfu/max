package com.laozhang.max_shiro.service;

import com.github.pagehelper.PageInfo;
import com.laozhang.max_shiro.entity.User;
import com.laozhang.max_shiro.vo.UserVO;

public interface UserService extends IService<User> {
    PageInfo<User> selectByPage(User user, int start, int length);

    User selectByUsername(String username);

    void delUser(Integer userid);

    UserVO getUserVO(User user);
}
