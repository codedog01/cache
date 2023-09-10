package com.ronhe.romp.cache.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hss
 */
public class DefaultCacheClassFactory implements CacheClassFactory {
    private Logger log = LoggerFactory.getLogger(DefaultCacheFactory.class);

    private ConcurrentMap<String, Class> classMap = new ConcurrentHashMap<String, Class>();

    private static CacheClassFactory cacheClassFactory = new DefaultCacheClassFactory();

    public static CacheClassFactory getInstance() {
        return cacheClassFactory;
    }

    @Override
    public Class getClassFromQualifiedName(String qualifiedName) {
        if (qualifiedName == null) {
            return null;
        }
        Class clazz = classMap.get(qualifiedName);
        if (clazz == null) {
            registryQualifiedName(qualifiedName);
            clazz = classMap.get(qualifiedName);
        }
        return clazz;
    }

    @Override
    public String getQualifiedNameFromObj(Object object) {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();
        return clazz.getName();
    }

    @Override
    public String getQualifiedNameFromClass(Class clazz) {
        String qualifiedName = clazz.getName();
        return qualifiedName;
    }

    @Override
    public void registryQualifiedName(String qualifiedName) {
        try {
            synchronized (classMap) {
                Class clazz = Class.forName(qualifiedName);
                classMap.put(qualifiedName, clazz);
            }
        } catch (ClassNotFoundException e) {
            log.info("注册缓存类型失败，没有找到对应的类型: [{}]", qualifiedName);
        }
    }
}
