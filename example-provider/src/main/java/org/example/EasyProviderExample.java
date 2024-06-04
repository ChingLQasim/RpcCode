package org.example;

import cn.islandecho.RpcApplication;
import cn.islandecho.register.LocalRegister;
import cn.islandecho.server.HttpServer;
import cn.islandecho.server.VertxHttpServer;
import org.example.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();
        LocalRegister.register(UserService.class.getName(), UserServiceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
