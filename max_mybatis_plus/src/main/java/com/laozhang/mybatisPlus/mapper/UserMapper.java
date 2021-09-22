package com.laozhang.mybatisPlus.mapper;

import com.laozhang.mybatisPlus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author laozhang
 * @since 2021-09-22
 */
public interface UserMapper extends BaseMapper<User> {

    int countAge();
}
