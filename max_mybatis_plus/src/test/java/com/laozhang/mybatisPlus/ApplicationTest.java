package com.laozhang.mybatisPlus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laozhang.mybatisPlus.entity.User;
import com.laozhang.mybatisPlus.mapper.UserMapper;
import com.laozhang.mybatisPlus.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {

        // 查询列表
        List<User> list = userService.list();
        System.out.println(list);

        // Wrappers lambdaQuery
        List<User> users = userService.list(Wrappers.<User>lambdaQuery()
                .eq(User::getName, "Jone"));
        System.out.println(users);

        // QueryWrapper
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 18);
        queryWrapper.orderByDesc("age");
        List<User> list1 = userService.list(queryWrapper);
        System.out.println(list1);

        // 分页
        IPage<User> page = userService.page(new Page<>(1, 2),
                Wrappers.<User>lambdaQuery().gt(User::getAge, 18).orderByAsc(User::getAge));
        System.out.println(page.getRecords());

        // 增
        User user = new User();
        user.setId(IdWorker.getId());
        user.setAge(23);
        user.setEmail("test6@baomidou.com");
        user.setName("BM");
        //userService.save(user);

        // 查
        User user1 = userService.getById(1440565804818100226L);
        System.out.println(user1);

        // 改
        user1.setName("BM");
        userService.updateById(user1);

        // 删除
        //userService.removeById(1440565804818100226L);

        // xml查询
        int count = userMapper.countAge();
        System.out.println("age > 20 : " + count);
    }
}
