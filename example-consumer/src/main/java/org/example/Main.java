package org.example;

import cn.islandecho.config.RpcConfig;
import cn.islandecho.utils.ConfigUtils;
import org.example.model.User;
import cn.islandecho.proxy.ServiceProxyFactory;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("hello");
        userService.getUser(user);
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc","dev");
        System.out.println(rpcConfig);
    }
}