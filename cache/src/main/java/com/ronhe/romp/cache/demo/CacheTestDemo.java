package com.ronhe.romp.cache.demo;

import com.ronhe.romp.cache.api.CacheUtil;
import com.ronhe.romp.cache.provider.CacheProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

public class CacheTestDemo {

    public static void main(String[] args) throws Exception {

        /*
         * // 2. 以工具类的方式使用 CacheUtil.put("user", "name:110", "张三"); Thread.sleep(1000);
         * CacheUtil.get("user", "name:110");
         *
         * System.out.println(CacheUtil.getCacheProvider());
         * System.out.println(CacheUtil.getCacheStatistics());
         */
    }
}
