package com.ronhe.romp.cache.sync;

/**
 * 缓存同步路径
 *
 * @author pc
 */
public interface SyncCachePath {
    String PREFIX = "/romp/cache";

    String GET_CACHE_DATA = "/getCacheData.do";

    String PUT_CACHE_DATA = "/putCacheData.do";

    String SYNC_CACHE_DATA = "/syncCacheData.do";
}
