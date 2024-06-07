package cn.islandecho.register;

import cn.islandecho.spi.SpiLoader;

public class RegisterFactory {
    static {
        SpiLoader.load(Register.class);
    }

    /**
     * 默认注册中心
     */
    private static final Register DEFAULT_SERIALIZER = new EtcdRegister();

    /**
     * 获取实例
     * @param registerName
     * @return
     */
    public static Register getInstance(String registerName) {
        return SpiLoader.getInstanceCache(Register.class,registerName);
    }
}
