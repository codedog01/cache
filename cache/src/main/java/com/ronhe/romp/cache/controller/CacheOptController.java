package com.ronhe.romp.cache.controller;

import com.ronhe.romp.cache.enums.CacheKvSerializerEnum;
import com.ronhe.romp.cache.enums.CacheOperatorType;
import com.ronhe.romp.cache.factory.CacheClassFactory;
import com.ronhe.romp.cache.factory.DefaultCacheClassFactory;
import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import com.ronhe.romp.cache.sync.CacheReqInfo;
import com.ronhe.romp.cache.sync.CacheResult;
import com.ronhe.romp.cache.sync.SyncCachePath;
import com.ronhe.romp.cache.wrapper.CacheWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存操作
 *
 * @author hss
 */
@RestController
@RequestMapping(SyncCachePath.PREFIX)
public class CacheOptController {
    private static Logger log = LoggerFactory.getLogger(CacheOptController.class);

    private CacheClassFactory cacheClassFactory = DefaultCacheClassFactory.getInstance();

    @Autowired
    private CacheManager cacheManager;

    private CacheKvSerializer cacheKvSerializer = CacheKvSerializerEnum.getSetupCacheKvSerializer();

    /**
     * 同步缓存信息
     *
     * @param reqInfo
     * @return
     */
    @RequestMapping(value = SyncCachePath.SYNC_CACHE_DATA, method = RequestMethod.POST)
    public CacheResult syncCacheData(@RequestBody CacheReqInfo reqInfo) {
        log.info("[Cache] 同步缓存数据开始，同步信息为: {}", reqInfo);

        String cacheName = reqInfo.getCacheName();
        if (cacheName == null) {
            return new CacheResult("4000", "缓存名称不能为空");
        }

        Integer operatorTypeCode = reqInfo.getOperatorType();
        if (operatorTypeCode == null) {
            return new CacheResult("4000", "操作方式不能为空");
        }
        CacheOperatorType operatorType = CacheOperatorType.of(operatorTypeCode);
        if (operatorType == null) {
            return new CacheResult("4000", "不存在的操作方式");
        }

        // 重要：这里一定要获取到原始的Cache，否则会形成死循环的去调用
        Cache cache;
        Object cacheObject = cacheManager.getCache(cacheName);
        if (cacheObject instanceof CacheWrapper) {
            cache = ((CacheWrapper) cacheObject).getCache();
        } else {
            cache = (Cache) cacheObject;
        }
        Class cacheKeyClass = cacheClassFactory.getClassFromQualifiedName(reqInfo.getCacheKeyClassName());
        Class cacheValClass = cacheClassFactory.getClassFromQualifiedName(reqInfo.getCacheValClassName());
        Object key = null;
        Object val = null;
        try {
            key = cacheKvSerializer.deserialize(reqInfo.getCacheKey(), cacheKeyClass);
            val = cacheKvSerializer.deserialize(reqInfo.getCacheVal(), cacheValClass);
        } catch (Throwable t) {
            log.error("无法完成对象的反序列化，异常信息为: {}", t);
            return new CacheResult();
        }

        switch (operatorType) {
            case PUT:
                if(val != null) cache.put(key, val);
                break;
            case PUT_IF_ABSENT:
                cache.putIfAbsent(key, val);
                break;
            case EVICT:
                cache.evict(key);
                break;
            case CLEAR:
                cache.clear();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return new CacheResult();
    }

    /**
     * 获取缓存信息
     *
     * @param reqInfo
     * @return
     */
    @RequestMapping(value = SyncCachePath.GET_CACHE_DATA, method = RequestMethod.POST)
    public CacheResult getCacheData(@RequestBody CacheReqInfo reqInfo) {
        log.info("[Cache] 获取缓存数据开始，请求信息为: {}", reqInfo);
        Cache cache = cacheManager.getCache(reqInfo.getCacheName());
        Class cacheKeyClass = cacheClassFactory.getClassFromQualifiedName(reqInfo.getCacheKeyClassName());
        Object key = null;
        try {
            key = cacheKvSerializer.deserialize(reqInfo.getCacheKey(), cacheKeyClass);
        } catch (Throwable t) {
            log.error("无法完成 key 的反序列化，异常信息为: {}", t);
            return new CacheResult();
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        CacheResult result = new CacheResult();
        if (valueWrapper == null) {
            return result;
        }
        result.setBody(valueWrapper.get());
        return result;
    }

    /**
     * 设置缓存信息
     *
     * @param reqInfo
     * @return
     */
    @RequestMapping(value = SyncCachePath.PUT_CACHE_DATA, method = RequestMethod.POST)
    public CacheResult putCacheData(@RequestBody CacheReqInfo reqInfo) {
        log.info("[Cache] 设置缓存数据开始，请求信息为: {}", reqInfo);
        Cache cache = cacheManager.getCache(reqInfo.getCacheName());
        Class cacheKeyClass = cacheClassFactory.getClassFromQualifiedName(reqInfo.getCacheKeyClassName());
        Class cacheValClass = cacheClassFactory.getClassFromQualifiedName(reqInfo.getCacheValClassName());
        Object key = null;
        Object val = null;
        try {
            key = cacheKvSerializer.deserialize(reqInfo.getCacheKey(), cacheKeyClass);
            val = cacheKvSerializer.deserialize(reqInfo.getCacheVal(), cacheValClass);
        } catch (Throwable t) {
            log.error("无法完成对象的反序列化，异常信息为: {}", t);
            return new CacheResult();
        }
        cache.put(key, val);
        return new CacheResult();
    }
}
