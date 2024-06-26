package cn.islandecho;


import cn.islandecho.config.RegisterConfig;
import cn.islandecho.config.RpcConfig;
import cn.islandecho.constant.RpcConstant;
import cn.islandecho.register.Register;
import cn.islandecho.register.RegisterFactory;
import cn.islandecho.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcApplication {

    //private static final Logger log = LoggerFactory.getLogger(RpcApplication.class);

    private static volatile RpcConfig rpcConfig;

    /**
     * 自定义初始化
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("RpcApplication init, rpcConfig = {}", rpcConfig.toString());
        RegisterConfig registerConfig = rpcConfig.getRegisterConfig();
        Register register = RegisterFactory.getInstance(registerConfig.getRegistry());
        register.init(registerConfig);
        log.info("register init, registerConfig = {}", registerConfig.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(register::destroy));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 加载失败采用默认配置
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}