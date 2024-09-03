package com.yangjl.bigevent.service;

import com.yangjl.bigevent.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);
}
