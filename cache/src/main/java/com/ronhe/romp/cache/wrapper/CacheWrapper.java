package com.ronhe.romp.cache.wrapper;

import com.ronhe.romp.cache.listener.CacheListener;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * 对Spring提供的Cache进行包装
 *
 * @author hss
 */
public class CacheWrapper implements Cache {
    private Cache cache;

    private CacheListener cacheListener;

    public CacheWrapper(Cache cache, CacheListener cacheListener) {
        this.cache = cache;
        this.cacheListener = cacheListener;
    }

    public Cache getCache() {
        return cache;
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return getNativeCache();
    }

    @Override
    public ValueWrapper get(Object cacheKey) {
        cacheListener.beforeGet(getName(), cacheKey);
        ValueWrapper valueWrapper = cache.get(cacheKey);
        cacheListener.afterGet(getName(), cacheKey, valueWrapper);
        return valueWrapper;
    }

    @Override
    public <T> T get(Object cacheKey, Class<T> aClass) {
        cacheListener.beforeGet(getName(), cacheKey);
        T t = cache.get(cacheKey, aClass);
        cacheListener.afterGet(getName(), cacheKey, t);
        return t;
    }

    @Override
    public <T> T get(Object cacheKey, Callable<T> callable) {
        cacheListener.beforeGet(getName(), cacheKey);
        T t = cache.get(cacheKey, callable);
        cacheListener.afterGet(getName(), cacheKey, t);
        return t;
    }

    @Override
    public void put(Object cacheKey, Object cacheVal) {
        cacheListener.beforePut(getName(), cacheKey, cacheVal);
        cache.put(cacheKey, cacheVal);
        cacheListener.afterPut(getName(), cacheKey, cacheVal);
    }

    @Override
    public ValueWrapper putIfAbsent(Object cacheKey, Object cacheVal) {
        cacheListener.beforePutIfAbsent(getName(), cacheKey, cacheVal);
        ValueWrapper valueWrapper = cache.putIfAbsent(cacheKey, cacheVal);
        cacheListener.afterPutIfAbsent(getName(), cacheKey, cacheVal);
        return valueWrapper;
    }

    @Override
    public void evict(Object cacheKey) {
        cacheListener.beforeEvict(getName(), cacheKey);
        cache.evict(cacheKey);
        cacheListener.afterEvict(getName(), cacheKey);
    }

    @Override
    public void clear() {
        cacheListener.beforeClear(getName());
        cache.clear();
        cacheListener.afterClear(getName());
    }
}
