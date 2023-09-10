package com.ronhe.romp.cache.serialize.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Jackson缓存键值序列化器
 *
 * @author hss
 */
public class JacksonCacheKvSerializer implements CacheKvSerializer {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger log = LoggerFactory.getLogger(JacksonCacheKvSerializer.class);

    @Override
    public byte[] serialize(Object object) throws Throwable {
        if (object == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.warn("Jackson 序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Throwable {
        if (bytes == null) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            log.warn("Jackson 反序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return t;
    }
}
