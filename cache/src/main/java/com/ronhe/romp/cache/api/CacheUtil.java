package com.ronhe.romp.cache.api;

import com.ronhe.romp.cache.config.CacheConfig;
import com.ronhe.romp.cache.consts.CacheConfigYamlKey;
import com.ronhe.romp.cache.factory.CacheFactory;
import com.ronhe.romp.cache.factory.DefaultCacheFactory;
import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.listener.DefaultCacheListener;
import com.ronhe.romp.cache.listener.MultiCacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 缓存工具类
 *
 * @author hss
 */
public class CacheUtil {
    private static Logger log = LoggerFactory.getLogger(CacheUtil.class);

    private static CacheFactory cacheFactory;

    private static CacheProvider cacheProvider;

    private static CacheConfig cacheConfig;

    static {
        init();
    }

    public static void init() {
        log.info("[Cache] CacheUtil开始初始化...");

        // 缓存配置，加载 romp-cache.yml 配置文件中的缓存配置
        CacheConfig cacheConfig = loadCacheConfig();

        // 创建缓存工厂，加入缓存监听器信息和缓存统计信息
        createCacheFactory();

        // 根据缓存配置创建缓存提供者
        createCacheProvider(cacheConfig);

        log.info("[Cache] CacheUtil初始化完成");
    }


    /**
     * loadCacheConfig 加载缓存配置
     */
    private static CacheConfig loadCacheConfig() {
        // 读取 yml文件中的配置
        ClassPathResource xmlCacheConfig = new ClassPathResource("cache/romp-cache.yml");
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        propertiesFactoryBean.setResources(xmlCacheConfig);
        Properties properties = propertiesFactoryBean.getObject();
        log.info("[Cache] 加载的缓存配置信息为:{}", properties);

        CacheConfig cacheConfig = new CacheConfig();
        // 获取并设置缓存类型配置
        String activeCacheType = properties.getProperty(CacheConfigYamlKey.ACTIVE_CACHE_TYPE);
        cacheConfig.setActiveCacheType(activeCacheType);
        // 激活缓存统计配置，如果为false则不进行统计
        Boolean enableCacheListener = Boolean.valueOf(properties.getProperty(CacheConfigYamlKey.ENABLE_CACHE_LISTENER, Boolean.FALSE.toString()));
        cacheConfig.setEnableCacheListener(enableCacheListener);

        CacheUtil.cacheConfig = cacheConfig;
        return cacheConfig;
    }

    /**
     * createCacheFactory 创建缓存工厂
     */
    public static void createCacheFactory() {
        // 创建默认的缓存工厂
        cacheFactory = new DefaultCacheFactory();

        // 加载缓存提供者
        cacheFactory.loadCacheProvider();

        // 注册缓存监听器
        List<CacheListener> listenerList = new ArrayList<>();
        listenerList.add(new DefaultCacheListener());
        MultiCacheListener multiCacheListener = new MultiCacheListener(listenerList);
        cacheFactory.registryListener(multiCacheListener);
    }

    public static void createCacheProvider(CacheConfig cacheConfig) {
        cacheProvider = cacheFactory.createCacheProvider(cacheConfig);
    }

    public static CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public static CacheConfig getCacheConfig() {
        return cacheConfig;
    }
}
