package com.laozhang.max_mybatis_annotation.mapper;

import com.laozhang.max_mybatis_annotation.entity.UserEntity;
import com.laozhang.max_mybatis_annotation.enums.UserSexEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from users")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();

    @Select("select * from users where id = #{id}")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO users(userName,passWord,user_sex,nick_name) VALUES(#{userName}, #{passWord}, #{userSex},#{nickName})")
    Long insert(UserEntity user);

    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);
}
