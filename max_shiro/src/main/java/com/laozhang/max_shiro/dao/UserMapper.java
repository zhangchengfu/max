package com.laozhang.max_shiro.dao;

import com.laozhang.max_shiro.entity.User;
import com.laozhang.max_shiro.entity.UserCriteria;

import java.util.List;

import com.laozhang.max_shiro.vo.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserCriteria example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserVO getUserVO(User user);
}