package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.entity.User;
import com.yangjl.bigevent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result resigter(String username, String password) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        userService.register(username, password);
        return Result.success();
    }

}
