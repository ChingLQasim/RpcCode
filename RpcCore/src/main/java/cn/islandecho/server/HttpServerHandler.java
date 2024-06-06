package cn.islandecho.server;

import cn.islandecho.RpcApplication;
import cn.islandecho.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import cn.islandecho.model.RpcRequest;
import cn.islandecho.model.RpcResponse;
import cn.islandecho.register.LocalRegister;
import cn.islandecho.serializer.JdkSerializer;
import cn.islandecho.serializer.Serializer;

import java.lang.reflect.Method;

public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        System.out.println("11111111111111" + serializer.getClass().getName());
        System.out.println("收到请求:" + httpServerRequest.method() + " " + httpServerRequest.uri());

        // 异步处理Http请求
        httpServerRequest.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                System.out.println("解析失败!");
                e.printStackTrace();
            }

            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("RpcRequest is null");
                doResponse(httpServerRequest, rpcResponse, serializer);
                return ;
            }
            try {
                Class<?> implClass = LocalRegister.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage("error");
                rpcResponse.setException(e);
            }
            doResponse(httpServerRequest, rpcResponse, serializer);
        });
    }

    void doResponse(HttpServerRequest httpServerRequest, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = httpServerRequest.response().putHeader("content-type", "application/json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (Exception e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
