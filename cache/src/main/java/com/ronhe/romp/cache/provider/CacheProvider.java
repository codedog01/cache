package com.ronhe.romp.cache.provider;

import com.ronhe.romp.cache.listener.CacheListener;
import org.springframework.cache.CacheManager;

/**
 * 缓存提供者
 *
 * @author hss
 */
public interface CacheProvider {
    /**
     * 初始化
     */
    void init();

    /**
     * 获取缓存类型
     *
     * @return 缓存类型
     */
    String getCacheType();

    /**
     * 获取缓存管理器
     *
     * @return 缓存管理器对象
     */
    CacheManager getCacheManager();

    /**
     * 获取缓存监听器，实现中不用写，直接返回null即可（CacheProviderWrapper中已经补充了）
     *
     * @return 获取缓存监听器
     */
    CacheListener getCacheListener();
}
