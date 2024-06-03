package org.example;

import org.example.model.User;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("当前获取到的用户" + user.getName());
        return user;
    }
}
