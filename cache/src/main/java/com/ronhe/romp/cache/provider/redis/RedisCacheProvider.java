package com.ronhe.romp.cache.provider.redis;


import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.listener.DefaultCacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import com.ronhe.romp.cache.util.SpringContextUtil;
import org.springframework.cache.CacheManager;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/11/2
 */
public class RedisCacheProvider implements CacheProvider {

    @Override
    public void init() {

    }

    @Override
    public String getCacheType() {
        return "redis";
    }

    @Override
    public CacheManager getCacheManager() {
        return new RedisConfiguration().redisCacheManager();
    }

    @Override
    public CacheListener getCacheListener() {
        return new DefaultCacheListener();
    }


}
