package com.laozhang.max_oauth2_server.mapper;

import com.laozhang.max_oauth2_server.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectByUserName(@Param("username") String username);
}
