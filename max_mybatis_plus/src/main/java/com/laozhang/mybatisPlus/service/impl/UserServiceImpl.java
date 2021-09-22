package com.laozhang.mybatisPlus.service.impl;

import com.laozhang.mybatisPlus.entity.User;
import com.laozhang.mybatisPlus.mapper.UserMapper;
import com.laozhang.mybatisPlus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author laozhang
 * @since 2021-09-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
