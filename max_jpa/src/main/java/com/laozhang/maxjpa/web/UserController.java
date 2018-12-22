package com.laozhang.maxjpa.web;

import com.laozhang.maxjpa.entity.User;
import com.laozhang.maxjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ResponseBody
    public List list() {
        List<User> users = userService.getUserList();
        return users;
    }

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody User user) {
        userService.save(user);
        return "success";
    }

    @PutMapping("/edit")
    @ResponseBody
    public String edit(@RequestBody User user) {
        userService.edit(user);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "success";
    }
}
