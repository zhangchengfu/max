package com.laozhang.shardingsphere.controller;

import com.laozhang.shardingsphere.entity.User;
import com.laozhang.shardingsphere.service.HealthLevelService;
import com.laozhang.shardingsphere.service.HealthRecordService;
import com.laozhang.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private HealthLevelService healthLevelService;

    @Autowired
    private HealthRecordService healthRecordService;

    @GetMapping("test")
    public Object test() throws Exception {

        //userService.processUsers();
        //healthRecordService.processHealthRecords();
        //healthLevelService.processLevels();
        List<User> list = userService.getUsers();
        return list;
    }

}
