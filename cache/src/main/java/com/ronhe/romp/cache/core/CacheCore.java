package com.ronhe.romp.cache.core;

import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import com.ronhe.romp.cache.provider.CacheProviderWrapper;

/**
 * 缓存核心包装器
 *
 * @author hss
 */
public class CacheCore {
    /**
     * 包装缓存提供者接口
     *
     * @param cacheProvider 缓存提供者接口
     * @param cacheListener 缓存监听器接口
     * @return
     */
    public static CacheProvider wrapCacheProvider(CacheProvider cacheProvider, CacheListener cacheListener) {
        return new CacheProviderWrapper(cacheProvider, cacheListener);
    }
}
