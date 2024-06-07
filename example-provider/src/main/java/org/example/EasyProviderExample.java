package org.example;

import cn.islandecho.RpcApplication;
import cn.islandecho.config.RegisterConfig;
import cn.islandecho.config.RpcConfig;
import cn.islandecho.model.ServiceMetaInfo;
import cn.islandecho.register.LocalRegister;
import cn.islandecho.register.Register;
import cn.islandecho.register.RegisterFactory;
import cn.islandecho.server.HttpServer;
import cn.islandecho.server.VertxHttpServer;
import org.example.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();
        String serviceName = UserService.class.getName();
        LocalRegister.register(serviceName, UserServiceImpl.class);
        // 获取RPC配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegisterConfig registerConfig = rpcConfig.getRegisterConfig();
        Register register = RegisterFactory.getInstance(registerConfig.getRegistry());
        // 根据配置装填服务信息
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        // 注册
        try {
            register.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //启动服务器
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
