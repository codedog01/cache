package com.ronhe.romp.cache.wrapper;

import com.ronhe.romp.cache.listener.CacheListener;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * 缓存管理器包装器
 *
 * @author hss
 */
public class CacheManagerWrapper implements CacheManager {
    private CacheManager cacheManager;

    private CacheListener cacheListener;

    public CacheManagerWrapper(CacheManager cacheManager, CacheListener cacheListener) {
        this.cacheManager = cacheManager;
        this.cacheListener = cacheListener;
    }

    @Override
    public Cache getCache(String cacheName) {
        return new CacheWrapper(cacheManager.getCache(cacheName), cacheListener);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }
}
