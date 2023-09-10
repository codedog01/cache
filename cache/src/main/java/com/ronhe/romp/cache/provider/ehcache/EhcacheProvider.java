package com.ronhe.romp.cache.provider.ehcache;

import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import com.ronhe.romp.cache.util.SpringContextUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

public class EhcacheProvider implements CacheProvider {

    @Override
    public String getCacheType() {
        return "EhCache";
    }

    @Override
    public void init() {
        ClassPathResource resource = new ClassPathResource("cache/romp-cache-ehcache.xml");
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EhCachePlugin ehCachePlugin = new EhCachePlugin(url);
        ehCachePlugin.start();
    }

    @Override
    public CacheManager getCacheManager() {
        return new EhCacheCacheManager(EhCachePlugin.getCacheManager());
    }

    @Override
    public CacheListener getCacheListener() {
        return null;
    }
}
