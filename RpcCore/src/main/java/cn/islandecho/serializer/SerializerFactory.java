package cn.islandecho.serializer;

import cn.islandecho.constant.SerializerConstant;
import cn.islandecho.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
//    /**
//     * 序列化映射
//     */
//    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer> () { {
//        put(SerializerConstant.JSON, new JSONSerializer());
//        put(SerializerConstant.JDK, new JdkSerializer());
//        put(SerializerConstant.KRYO, new KryoSerialier());
//        put(SerializerConstant.HESSIAN, new HessianSerializer());
//    } };


    /**
     * 默认实例化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     * @param serializerName
     * @return
     */
    public static Serializer getInstance(String serializerName) {
        return null;
    }
}
