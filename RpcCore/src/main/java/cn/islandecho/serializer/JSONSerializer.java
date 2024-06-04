package cn.islandecho.serializer;


import cn.islandecho.model.RpcRequest;
import cn.islandecho.model.RpcResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON序列化器
 */
public class JSONSerializer implements Serializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        T obj = OBJECT_MAPPER.readValue(data, clazz);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, clazz);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, clazz);
        }
        return obj;
    }

    /**
     * 处理Object类型对象反序列化后成为LinkedHashMap的问题
     * @param rpcRequest
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> clazz) throws Exception {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getArgs();
        // 处理每个参数的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.isAssignableFrom(parameters[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(parameters[i]);
                parameters[i] = OBJECT_MAPPER.readValue(argBytes, parameterType);
            }
        }
        return clazz.cast(rpcRequest);
    }

    /**
     * 处理相应数据
     * @param rpcResponse
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> clazz) throws Exception {
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes,rpcResponse.getDataType()));
        return clazz.cast(rpcResponse);
    }

}
