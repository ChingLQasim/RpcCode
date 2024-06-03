package org.example.serializer;

public interface Serializer {
    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws Exception
     */
    <T> byte[] serialize(T obj) throws Exception;

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;


}
