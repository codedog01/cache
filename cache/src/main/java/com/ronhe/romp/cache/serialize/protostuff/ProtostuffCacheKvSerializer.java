package com.ronhe.romp.cache.serialize.protostuff;

import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protoBuf缓存键值序列化器
 *
 * @author hss
 */
public class ProtostuffCacheKvSerializer implements CacheKvSerializer {
    private Logger log = LoggerFactory.getLogger(ProtostuffCacheKvSerializer.class);

    @Override
    public byte[] serialize(Object object) throws Throwable {
        if (object == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            Schema schema = RuntimeSchema.getSchema(object.getClass());
            bytes = ProtostuffIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(256));
        } catch (Exception e) {
            log.error("Protostuff 序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Throwable {
        if (bytes == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (Exception e) {
            log.error("Protostuff 反序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return obj;
    }
}
