package org.example;

import org.example.model.User;
import org.example.proxy.ServiceProxyFactory;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("hello");
        userService.getUser(user);
    }
}