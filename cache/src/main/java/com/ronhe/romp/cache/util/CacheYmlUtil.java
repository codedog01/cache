package com.ronhe.romp.cache.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * 缓存yml配置工具类
 *
 * @author hss
 */
public class CacheYmlUtil {
    public static Properties cacheConfigProperties;

    /**
     * 获取缓存配置属性
     *
     * @return 属性值
     */
    public static Properties getCacheConfigProperties() {
        return cacheConfigProperties;
    }

    /**
     * 根据属性 key 获取属性值
     *
     * @param propKey 属性 key
     * @return 属性值
     */
    public static String getPropVal(String propKey) {
        return cacheConfigProperties.getProperty(propKey);
    }

    /**
     * 根据属性 key 获取属性值
     *
     * @param propKey    属性 key
     * @param defaultVal 默认属性值
     * @return 属性值
     */
    public static String getPropVal(String propKey, String defaultVal) {
        return cacheConfigProperties.getProperty(propKey, defaultVal);
    }

    static {
        cacheConfigProperties = loadPropertiesFromYml("cache/romp-cache.yml");
    }

    /**
     * 从 yml 路径中加载 yml 文件并读取配置转为 properties
     *
     * @param ymlPath
     * @return
     */
    public static Properties loadPropertiesFromYml(String ymlPath) {
        ClassPathResource xmlCacheConfig = new ClassPathResource(ymlPath);
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        propertiesFactoryBean.setResources(xmlCacheConfig);
        return propertiesFactoryBean.getObject();
    }
}
