package org.example;

import cn.islandecho.RpcApplication;
import cn.islandecho.config.RpcConfig;
import cn.islandecho.register.LocalRegister;
import cn.islandecho.server.HttpServer;
import cn.islandecho.server.VertxHttpServer;
import cn.islandecho.utils.ConfigUtils;
import org.example.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {
        LocalRegister.register(UserService.class.getName(), UserServiceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
