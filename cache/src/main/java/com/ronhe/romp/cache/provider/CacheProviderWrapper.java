package com.ronhe.romp.cache.provider;

import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.wrapper.CacheManagerWrapper;
import org.springframework.cache.CacheManager;

/**
 * 缓存提供者包装器
 *
 * @author hss
 */
public final class CacheProviderWrapper implements CacheProvider {
    private CacheProvider cacheProvider;

    private CacheListener cacheListener;

    public CacheProviderWrapper(CacheProvider cacheProvider, CacheListener cacheListener) {
        this.cacheProvider = cacheProvider;
        this.cacheListener = cacheListener;
    }

    @Override
    public void init() {
        cacheProvider.init();
    }

    @Override
    public String getCacheType() {
        return cacheProvider.getCacheType();
    }

    @Override
    public CacheManager getCacheManager() {
        return new CacheManagerWrapper(cacheProvider.getCacheManager(), cacheListener);
    }

    @Override
    public CacheListener getCacheListener() {
        return cacheListener;
    }
}
