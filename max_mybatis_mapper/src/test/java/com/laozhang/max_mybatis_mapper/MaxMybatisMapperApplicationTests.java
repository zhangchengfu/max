package com.laozhang.max_mybatis_mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laozhang.max_mybatis_mapper.bean.User;
import com.laozhang.max_mybatis_mapper.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxMybatisMapperApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Autowired
//    private UserService userService;

    @Test
    public void testUserService() {
//        User user = new User();
//        user.setEnable(1);
//        user.setPassword("123456");
//        user.setSalt("test");
//        user.setUsername("mapper");
//        userService.save(user);
//        System.out.println("*****************88");
//        System.out.println("id:" + user.getId());
//        System.out.println("*****************88");


//		Example example = new Example(User.class);
//		example.createCriteria().andCondition("username like '%1%'")
//                .andEqualTo("salt",0)
//        ;
//		example.setOrderByClause("id desc");
//		List<User> userList = this.userService.selectByExample(example);
//		for (User u : userList) {
//			System.out.println(u.getUsername());
//		}

//		List<User> all = this.userService.selectAll();
//		for (User u : all) {
//			System.out.println(u.getUsername());
//		}
//
//		User user = new User();
//		user.setId(1);
//		user = this.userService.selectByKey(user);
//		System.out.println(user.getUsername());
//
//		user.setId(23);
//		this.userService.delete(user);

//        PageHelper.startPage(2, 3);
//        List<User> list = userService.selectAll();
//        PageInfo<User> pageInfo = new PageInfo<User>(list);
//        List<User> result = pageInfo.getList();
//        for (User u : result) {
//            System.out.println(u.getUsername());
//        }
    }

}
