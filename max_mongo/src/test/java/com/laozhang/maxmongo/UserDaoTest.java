package com.laozhang.maxmongo;

import com.laozhang.maxmongo.dao.UserDao;
import com.laozhang.maxmongo.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testSaveUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(21L);
        user.setUserName("小明");
        user.setPassWord("1230");
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName() {
        UserEntity user = userDao.findUserByUserName("小明");
        System.out.println("user is " + user);
    }

    @Test
    public void updatUser() {
        UserEntity user = new UserEntity();
        user.setId(21L);
        user.setUserName("天空");
        user.setPassWord("sfsdfsdf");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById() {
        userDao.deleteUserById(21L);
    }
}
