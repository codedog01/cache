	 package com.ronhe.romp.cache.listener;

import com.ronhe.romp.cache.enums.CacheKvSerializerEnum;
import com.ronhe.romp.cache.enums.CacheOperatorType;
import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import com.ronhe.romp.cache.sync.CacheReqInfo;
import com.ronhe.romp.cache.sync.SyncCacheClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步缓存监听器
 *
 * @author hss
 */
public class SyncCacheListener implements CacheListener {
    private Logger log = LoggerFactory.getLogger(SyncCacheListener.class);

    private CacheKvSerializer cacheKvSerializer = CacheKvSerializerEnum.getSetupCacheKvSerializer();

    @Override
    public void beforePut(String cacheName, Object cacheKey, Object cacheVal) {

    }

    @Override
    public void afterPut(String cacheName, Object cacheKey, Object cacheVal) {
        CacheReqInfo syncInfo = new CacheReqInfo();
        syncInfo.setOperatorType(CacheOperatorType.PUT.getCode());
        syncInfo.setCacheName(cacheName);
        try {
            syncInfo.setCacheKey(cacheKvSerializer.serialize(cacheKey));
            syncInfo.setCacheKeyClassName(cacheKey == null ? null : cacheKey.getClass().getName());
            syncInfo.setCacheVal(cacheKvSerializer.serialize(cacheVal));
            syncInfo.setCacheValClassName(cacheVal == null ? null : cacheVal.getClass().getName());
        } catch (Throwable throwable) {
            log.error("缓存对象序列化异常，不发生同步操作，异常信息为: {}", throwable);
            return;
        }
        SyncCacheClient.syncCacheData(syncInfo);
    }

    @Override
    public void beforeGet(String cacheName, Object cacheKey) {

    }

    @Override
    public void afterGet(String cacheName, Object cacheKey, Object cacheVal) {

    }

    @Override
    public void beforePutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {

    }

    @Override
    public void afterPutIfAbsent(String cacheName, Object cacheKey, Object cacheVal) {
        CacheReqInfo syncInfo = new CacheReqInfo();
        syncInfo.setOperatorType(CacheOperatorType.PUT_IF_ABSENT.getCode());
        syncInfo.setCacheName(cacheName);
        try {
            syncInfo.setCacheKey(cacheKvSerializer.serialize(cacheKey));
            syncInfo.setCacheKeyClassName(cacheKey == null ? null : cacheKey.getClass().getName());
            syncInfo.setCacheVal(cacheKvSerializer.serialize(cacheVal));
            syncInfo.setCacheValClassName(cacheVal == null ? null : cacheVal.getClass().getName());
        } catch (Throwable throwable) {
            log.error("缓存对象序列化异常，不发生同步操作，异常信息为: {}", throwable);
            return;
        }
        SyncCacheClient.syncCacheData(syncInfo);
    }

    @Override
    public void beforeEvict(String cacheName, Object cacheKey) {

    }

    @Override
    public void afterEvict(String cacheName, Object cacheKey) {
        CacheReqInfo syncInfo = new CacheReqInfo();
        syncInfo.setOperatorType(CacheOperatorType.EVICT.getCode());
        syncInfo.setCacheName(cacheName);
        try {
            syncInfo.setCacheKey(cacheKvSerializer.serialize(cacheKey));
            syncInfo.setCacheKeyClassName(cacheKey == null ? null : cacheKey.getClass().getName());
        } catch (Throwable throwable) {
            log.error("缓存对象序列化异常，不发生同步操作，异常信息为: {}", throwable);
            return;
        }
        SyncCacheClient.syncCacheData(syncInfo);
    }

    @Override
    public void beforeClear(String cacheName) {

    }

    @Override
    public void afterClear(String cacheName) {
        CacheReqInfo syncInfo = new CacheReqInfo();
        syncInfo.setOperatorType(CacheOperatorType.CLEAR.getCode());
        syncInfo.setCacheName(cacheName);
        SyncCacheClient.syncCacheData(syncInfo);
    }
}
