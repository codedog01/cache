package com.ronhe.romp.cache.spring;

import com.ronhe.romp.cache.config.CacheConfig;
import com.ronhe.romp.cache.factory.CacheFactory;
import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import com.ronhe.romp.cache.wrapper.CacheManagerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.CacheManager;

/**
 * 默认的缓存管理器创建工厂
 *
 * @author hss
 */
public class DefaultCacheManagerFactoryBean implements FactoryBean {
    private CacheProvider cacheProvider;

    public DefaultCacheManagerFactoryBean(CacheFactory cacheFactory, CacheConfig cacheConfig) {
        this.cacheProvider = cacheFactory.createCacheProvider(cacheConfig);
    }

    @Override
    public Object getObject() throws Exception {
        CacheManager cacheManager = cacheProvider.getCacheManager();
        return cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
