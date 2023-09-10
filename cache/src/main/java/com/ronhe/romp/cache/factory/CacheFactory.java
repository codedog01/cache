package com.ronhe.romp.cache.factory;

import com.ronhe.romp.cache.config.CacheConfig;
import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;

/**
 * 缓存工厂接口
 *
 * @author hss
 */
public interface CacheFactory {
    /**
     * 创建缓存提供者
     *
     * @param cacheConfig 缓存配置
     * @return
     */
    CacheProvider createCacheProvider(CacheConfig cacheConfig);

    /**
     * 注册缓存监听器
     *
     * @param cacheListener 缓存监听器
     */
    void registryListener(CacheListener cacheListener);

    /**
     * 加载缓存提供者 SPI
     */
    void loadCacheProvider();
}
