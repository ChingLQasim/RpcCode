package cn.islandecho.config;

import cn.islandecho.constant.SerializerConstant;
import lombok.Data;

@Data
public class RpcConfig {
    /**
     * RP名称
     */
    private String name = "RpcCore";

    /**
     * 版本号码
     */
    private String version = "1.0.0";

    /**
     * ip地址
     */
    private String serverHost = "127.0.0.1";

    /**
     * 端口号
     */
    private Integer serverPort = 8888;

    /**
     * 序列化器
     */
    private String serializer = SerializerConstant.JDK;
}
