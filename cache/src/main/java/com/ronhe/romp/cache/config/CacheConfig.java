package com.ronhe.romp.cache.config;

/**
 * 缓存配置
 *
 * @author hss
 */
public class CacheConfig {
    private String activeCacheType;

    private boolean enableCacheListener;

    public String getActiveCacheType() {
        return activeCacheType;
    }

    public void setActiveCacheType(String activeCacheType) {
        this.activeCacheType = activeCacheType;
    }

    public boolean isEnableCacheListener() {
        return enableCacheListener;
    }

    public void setEnableCacheListener(boolean enableCacheListener) {
        this.enableCacheListener = enableCacheListener;
    }
}
