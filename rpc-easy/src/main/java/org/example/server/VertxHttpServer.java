package org.example.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
//        httpServer.requestHandler(req -> {
//            System.out.println("接收到请求:" + req.method() + " 请求地址:" + req.uri());
//            req.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("已经成功从Vertx建立链接！");
//        });
        httpServer.requestHandler(new HttpServerHandler());
        httpServer.listen(port, httpServerAsyncResult -> {
            if (httpServerAsyncResult.succeeded()) {
                System.out.println("当前Vertx正在监听端口:" + port);
            } else {
                System.out.println("启动Vertx服务器失败！");
            }
        });
    }
}
