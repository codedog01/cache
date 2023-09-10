package com.ronhe.romp.cache.factory;

import com.ronhe.romp.cache.CacheProviderInfo;
import com.ronhe.romp.cache.config.CacheConfig;
import com.ronhe.romp.cache.core.CacheCore;
import com.ronhe.romp.cache.listener.CacheListener;
import com.ronhe.romp.cache.provider.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认的缓存工厂
 *
 * @author hss
 */
public class DefaultCacheFactory implements CacheFactory {
    private static Logger log = LoggerFactory.getLogger(DefaultCacheFactory.class);

    private final static CopyOnWriteArrayList<CacheProviderInfo> registeredCacheProviders = new CopyOnWriteArrayList<>();

    private CacheListener cacheListener;

    public DefaultCacheFactory() {
    }

    public void setCacheListener(CacheListener cacheListener) {
        log.warn("[Cache] 缓存工厂中设置了缓存监听器，信息为: {}", cacheListener.getClass().getSimpleName());
        this.cacheListener = cacheListener;
    }

    /**
     * 注册缓存提供者实例
     *
     * @param provider 缓存提供者实例
     */
    public synchronized void registerCacheProvider(CacheProvider provider) {
        if (provider != null) {
            registeredCacheProviders.addIfAbsent(new CacheProviderInfo(provider.getCacheType(), provider));
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 加载缓存提供者
     */
    @Override
    public void loadCacheProvider() {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                ServiceLoader<CacheProvider> loadedProviders = ServiceLoader.load(CacheProvider.class);
                Iterator<CacheProvider> providerIterator = loadedProviders.iterator();

                try {
                    while (providerIterator.hasNext()) {
                        CacheProvider provider = providerIterator.next();
                        registerCacheProvider(provider);

                        log.info("[Cache] 加载的CacheProvider类型为：{}", provider.getCacheType());
                    }
                } catch (Throwable t) {
                    // Do nothing
                }
                return null;
            }
        });
    }

    /**
     * 根据缓存提供者名称获取缓存提供者实例
     *
     * @param cacheProviderName 缓存提供者实例名称
     * @return
     */
    public synchronized CacheProvider getCacheProvider(String cacheProviderName) {
        for (CacheProviderInfo providerInfo : registeredCacheProviders) {
            if (providerInfo.getCacheTypeName().equalsIgnoreCase(cacheProviderName)) {
                return providerInfo.getCacheProvider();
            }
        }
        return null;
    }


    /**
     * 根据缓存配置信息创建缓存提供者实例
     *
     * @param cacheConfig 缓存配置信息
     * @return
     */
    @Override
    public CacheProvider createCacheProvider(CacheConfig cacheConfig) {
        String cacheProviderName = cacheConfig.getActiveCacheType();
        CacheProvider cacheProvider = getCacheProvider(cacheProviderName);
        cacheProvider.init();
        if (cacheConfig.isEnableCacheListener()) {
            if (null == cacheListener) {
                log.warn("[Cache] 缓存监听配置已开启，但没有配置任何缓存监听器");
                return cacheProvider;
            } else {
                log.info("[Cache] 缓存监听配置已开启，监听器名称为: {}", cacheListener.getClass().getSimpleName());
                return CacheCore.wrapCacheProvider(cacheProvider, cacheListener);
            }
        } else {
            return cacheProvider;
        }
    }

    @Override
    public void registryListener(CacheListener cacheListener) {
        log.warn("[Cache] 缓存工厂中注册了新的缓存监听器，信息为: {}", cacheListener.getClass().getSimpleName());
        this.cacheListener = cacheListener;
    }
}
