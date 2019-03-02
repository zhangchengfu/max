package com.laozhang.max_mybatis_xml;

import com.laozhang.max_mybatis_xml.entity.UserEntity;
import com.laozhang.max_mybatis_xml.enums.UserSexEnum;
import com.laozhang.max_mybatis_xml.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxMybatisXmlApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insert() {
        UserEntity entity = new UserEntity();
        entity.setNickName("haha");
        entity.setPassWord("haha");
        entity.setUserName("haha");
        entity.setUserSex(UserSexEnum.MAN);
        userMapper.insert(entity);
        org.junit.Assert.assertEquals(entity.getId(),null);
    }

}

