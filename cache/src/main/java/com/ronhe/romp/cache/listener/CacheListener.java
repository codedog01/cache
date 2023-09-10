package com.ronhe.romp.cache.listener;

/**
 * 缓存监听器接口
 *
 * @author hss
 */
public interface CacheListener {

    /**
     * put 前操作
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheVal
     */
    void beforePut(String cacheName, Object cacheKey, Object cacheVal);

    /**
     * put 后操作
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheVal
     */
    void afterPut(String cacheName, Object cacheKey, Object cacheVal);

    /**
     * get 前操作
     *
     * @param cacheName
     * @param cacheKey
     */
    void beforeGet(String cacheName, Object cacheKey);

    /**
     * get 后操作
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheVal
     */
    void afterGet(String cacheName, Object cacheKey, Object cacheVal);

    /**
     * putIfAbsent 前操作
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheVal
     */
    void beforePutIfAbsent(String cacheName, Object cacheKey, Object cacheVal);

    /**
     * putIfAbsent 后操作
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheVal
     */
    void afterPutIfAbsent(String cacheName, Object cacheKey, Object cacheVal);

    /**
     * evict 前操作
     *
     * @param cacheName
     * @param cacheKey
     */
    void beforeEvict(String cacheName, Object cacheKey);

    /**
     * evict 后操作
     *
     * @param cacheName
     * @param cacheKey
     */
    void afterEvict(String cacheName, Object cacheKey);

    /**
     * clear 前操作
     *
     * @param cacheName
     */
    void beforeClear(String cacheName);

    /**
     * clear 后操作
     *
     * @param cacheName
     */
    void afterClear(String cacheName);
}
