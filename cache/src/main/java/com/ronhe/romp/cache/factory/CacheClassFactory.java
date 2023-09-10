package com.ronhe.romp.cache.factory;

/**
 * @author hss
 */
public interface CacheClassFactory {
    /**
     * 根据全限定类名获取类的类型
     *
     * @param qualifiedName 全限定类名
     * @return 类型
     */
    Class getClassFromQualifiedName(String qualifiedName);

    /**
     * 根据对象获取全限定类名
     *
     * @param object 对象
     * @return 全限定类名
     */
    String getQualifiedNameFromObj(Object object);

    /**
     * 根据类获取全限定类名
     *
     * @param clazz 类的类型
     * @return 全限定类名
     */
    String getQualifiedNameFromClass(Class clazz);

    /**
     * 注册类的全限定名
     *
     * @param qualifiedName 全限定类名
     */
    void registryQualifiedName(String qualifiedName);
}
