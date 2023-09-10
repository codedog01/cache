package com.ronhe.romp.cache.listener;

import java.util.List;

/**
 * 复合缓存监听器
 *
 * @author hss
 */
public class MultiCacheListener implements CacheListener {
    private List<CacheListener> cacheListenerList;

    public MultiCacheListener() {
    }

    public MultiCacheListener(List<CacheListener> cacheListenerList) {
        this.cacheListenerList = cacheListenerList;
    }

    public void addCacheListener(CacheListener cacheListener) {
        this.cacheListenerList.add(cacheListener);
    }

    @Override
    public void beforePut(String cacheName, Object cacheKey, Object cacheVal) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.beforePut(cacheName, cacheKey, cacheVal);
        }
    }

    @Override
    public void afterPut(String cacheName, Object cacheKey, Object cacheVal) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.afterPut(cacheName, cacheKey, cacheVal);
        }
    }

    @Override
    public void beforeGet(String cacheName, Object cacheKey) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.beforeGet(cacheName, cacheKey);
        }
    }

    @Override
    public void afterGet(String cacheName, Object cacheKey, Object cacheVal) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.afterGet(cacheName, cacheKey, cacheVal);
        }
    }

    @Override
    public void beforePutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.beforePutIfAbsent(cacheName, cacheKey, cacheVal);
        }
    }

    @Override
    public void afterPutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.afterPutIfAbsent(cacheName, cacheKey, cacheVal);
        }
    }

    @Override
    public void beforeEvict(String cacheName, Object cacheKey) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.beforeEvict(cacheName, cacheKey);
        }
    }

    @Override
    public void afterEvict(String cacheName, Object cacheKey) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.afterEvict(cacheName, cacheKey);
        }
    }

    @Override
    public void beforeClear(String cacheName) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.beforeClear(cacheName);
        }
    }

    @Override
    public void afterClear(String cacheName) {
        for (CacheListener cacheListener : cacheListenerList) {
            cacheListener.afterClear(cacheName);
        }
    }
}
