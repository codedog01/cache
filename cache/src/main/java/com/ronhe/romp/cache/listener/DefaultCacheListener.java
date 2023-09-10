package com.ronhe.romp.cache.listener;

		 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存监听器默认实现
 *
 * @author hss
 */
public class DefaultCacheListener implements CacheListener {
    private static Logger log = LoggerFactory.getLogger(DefaultCacheListener.class);

    @Override
    public void beforePut(String cacheName, Object cacheKey, Object cacheVal) {
        log.info("设置缓存值开始，缓存key为:{}", cacheKey);
    }

    @Override
    public void afterPut(String cacheName, Object cacheKey, Object cacheVal) {
        log.info("设置缓存值结束，缓存key为:{}", cacheKey);
    }

    @Override
    public void beforeGet(String cacheName, Object cacheKey) {
        log.info("获取缓存值开始，缓存key为:{}", cacheKey);
    }

    @Override
    public void afterGet(String cacheName, Object cacheKey, Object cacheVal) {
        log.info("获取缓存值结束，缓存key为:{}", cacheKey);
    }

    @Override
    public void beforePutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {
        log.info("设置缓存值（putIfAbsent）开始，缓存key为:{}", cacheKey);
    }

    @Override
    public void afterPutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {
        log.info("设置缓存值（putIfAbsent）结束，缓存key为:{}", cacheKey);
    }

    @Override
    public void beforeEvict(String cacheName, Object cacheKey) {
        log.info("清除缓存开始，缓存key为:{}", cacheKey);
    }

    @Override
    public void afterEvict(String cacheName, Object cacheKey) {
        log.info("清除缓存结束，缓存key为:{}", cacheKey);
    }

    @Override
    public void beforeClear(String cacheName) {
        log.info("清空缓存开始");
    }

    @Override
    public void afterClear(String cacheName) {
        log.info("清空缓存结束");
    }
}
