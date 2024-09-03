package com.yangjl.bigevent.service.impl;

import com.yangjl.bigevent.entity.User;
import com.yangjl.bigevent.mapper.UserMapper;
import com.yangjl.bigevent.service.UserService;
import com.yangjl.bigevent.utils.Md5Util;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 添加
        userMapper.add(username, md5String);
    }
}
