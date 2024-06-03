package org.example;

import org.example.register.LocalRegister;
import org.example.server.HttpServer;
import org.example.server.VertxHttpServer;
import org.example.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {
        LocalRegister.register(UserService.class.getName(), UserServiceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
