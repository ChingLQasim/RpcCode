package cn.islandecho.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.islandecho.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SpiLoader {
    /**
     * 存储已经加载的类
     */
    private static Map<String,Map<String, Class<?>>> loadMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存
     */
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system";

    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    /**
     * 动态加载的类列表
     */
    private static final List<Class<?>> LOAD_CLASSES = Arrays.asList(Serializer.class);

    /**
     * 加载所有的类
     */
    public static void loadAll() {
        for (Class<?> clazz : LOAD_CLASSES) {
            load(clazz);
        }
    }

    /**
     * 加载类
     * @param clazz
     * @return
     */
    public static Map<String, Class<?>> load(Class<?> clazz) {
      log.info("加载的类型为 {} 的SPI", clazz.getName());
      Map<String, Class<?>> keyClassMap = new HashMap<>();
      for (String scanDir : SCAN_DIRS) {
          List<URL> resources = ResourceUtil.getResources(scanDir + clazz.getName());
          // 读取每个资源文件
          for (URL resource : resources) {
              try {
                  InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                  String line;
                  while ((line = bufferedReader.readLine()) != null) {
                      String[] strArray = line.split("=");
                      if (strArray.length > 1) {
                          String key = strArray[0];
                          String className = strArray[1];
                          keyClassMap.put(key, Class.forName(className));
                      }
                  }
              } catch (Exception e) {
                  log.error("spi resource load error", e);
              }
          }
      }
      loadMap.put(clazz.getName(), keyClassMap);
      return keyClassMap;
    }

    public static <T> T getInstanceCache(Class<?> tClass, String key) {
        String className = tClass.getName();
        Map<String, Class<?>> classMap = loadMap.get(className);
        if (classMap == null) {
            throw new RuntimeException(String.format("SPILoader 未加载 %s 类型", className));
        }
        if (!classMap.containsKey(key)) {
            throw new RuntimeException(String.format("SPILoader加载的%s，不存在key=%s的类", className,key));
        }
        // 获取到需要加载的类
        Class<?> implClazz = classMap.get(key);
        // 从实例缓存中加载对应的实例化的对象
        String implClassName = implClazz.getName();
        if (!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(String.format("%s 实例化失败", implClassName));
            }
        }
        return (T) instanceCache.get(implClassName);
    }


}
