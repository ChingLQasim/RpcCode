package org.example;

import cn.islandecho.RpcApplication;
import cn.islandecho.config.RpcConfig;
import cn.islandecho.utils.ConfigUtils;
import org.example.model.User;
import cn.islandecho.proxy.ServiceProxyFactory;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        RpcApplication.init(ConfigUtils.loadConfig(RpcConfig.class, "rpc", "dev"));
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("hello");
        userService.getUser(user);
        //System.out.println(rpcConfig);
    }
}