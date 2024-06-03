package cn.islandecho.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegister {
    /**
     * 服务储存
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 服务注册
     * @param serviceName
     * @param clazz
     */
    public static void register(String serviceName, Class<?> clazz) {
        map.put(serviceName, clazz);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }

}
