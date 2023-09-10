package com.ronhe.romp.cache.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 缓存键值序列化器
 *
 * @author hss
 */
public interface CacheKvSerializer {
    /**
     * 将对象序列化为 byte 数组
     *
     * @param object 序列化的对象
     * @return
     */
    byte[] serialize(Object object) throws Throwable;

    /**
     * 将 byte 数组序列化为指定类型
     *
     * @param bytes byte 数组
     * @param clazz 类型
     * @param <T>   泛型 T，和 clazz 类型一致
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws Throwable;
}
