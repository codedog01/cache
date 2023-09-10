package com.ronhe.romp.cache;

import com.ronhe.romp.cache.api.CacheUtil;
import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import com.ronhe.romp.cache.spring.DefaultCacheManagerFactoryBean;
import com.ronhe.romp.cache.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/11/3
 */
@SpringBootApplication
@EnableCaching
@ImportResource("classpath:cache/romp-cache.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

        /*TODO: 方式一 使用CacheUtil*/
        CacheProvider cacheProvider = CacheUtil.getCacheProvider();
        CacheManager cacheManager = cacheProvider.getCacheManager();
        Cache cache1 = cacheManager.getCache("cache");
        cache1.put("key", 111211);
        Cache.ValueWrapper ke1y = cache1.get("ke1y");
        Object value1 = ke1y.get();
        System.out.println("get key by CacheUtil =============================== " + value1);

        /*TODO: 方式二 通过IOC容器获取*/
//        CacheManager cacheManager2 = SpringContextUtil.getBean(CacheManager.class);
//        Cache cache2 = cacheManager2.getCache("cache");
//        Object value2 = cache2.get("key").get();
//        System.out.println("get key by SpringContext =========================== " + value2);

    }



}
