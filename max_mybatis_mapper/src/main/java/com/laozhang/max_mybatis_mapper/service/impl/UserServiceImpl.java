package com.laozhang.max_mybatis_mapper.service.impl;

import com.laozhang.max_mybatis_mapper.bean.User;
import com.laozhang.max_mybatis_mapper.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

}
