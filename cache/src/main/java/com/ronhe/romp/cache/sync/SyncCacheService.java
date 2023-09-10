package com.ronhe.romp.cache.sync;

import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 缓存同步服务接口
 *
 * @author hss
 */
public interface SyncCacheService {
    /**
     * 同步缓存信息
     *
     * @param syncInfo
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("POST " + SyncCachePath.PREFIX + SyncCachePath.SYNC_CACHE_DATA)
    CacheResult syncCacheData(CacheReqInfo syncInfo);

    /**
     * 获取缓存信息
     *
     * @param syncInfo
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("POST " + SyncCachePath.PREFIX + SyncCachePath.GET_CACHE_DATA)
    CacheResult getCacheData(CacheReqInfo syncInfo);

    /**
     * 设置缓存信息
     *
     * @param syncInfo
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @RequestLine("POST " + SyncCachePath.PREFIX + SyncCachePath.PUT_CACHE_DATA)
    CacheResult putCacheData(CacheReqInfo syncInfo);
}
