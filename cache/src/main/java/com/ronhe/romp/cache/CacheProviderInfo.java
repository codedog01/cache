package com.ronhe.romp.cache;

import com.ronhe.romp.cache.provider.CacheProvider;

/**
 * 缓存提供者信息
 *
 * @author hss
 */
public class CacheProviderInfo {
    private String cacheTypeName;

    private CacheProvider cacheProvider;

    public CacheProviderInfo(String cacheTypeName, CacheProvider cacheProvider) {
        if (cacheTypeName == null) {
            this.cacheTypeName = cacheProvider.getClass().getSimpleName();
        } else {
            this.cacheTypeName = cacheTypeName;
        }
        this.cacheProvider = cacheProvider;
    }

    public String getCacheTypeName() {
        return cacheTypeName;
    }

    public void setCacheTypeName(String cacheTypeName) {
        this.cacheTypeName = cacheTypeName;
    }

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }
}
