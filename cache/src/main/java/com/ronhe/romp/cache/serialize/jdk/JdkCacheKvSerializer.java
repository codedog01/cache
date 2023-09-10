package com.ronhe.romp.cache.serialize.jdk;

import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * jdk缓存键值序列化器
 *
 * @author hss
 */
public class JdkCacheKvSerializer implements CacheKvSerializer {
    private Logger log = LoggerFactory.getLogger(JdkCacheKvSerializer.class);

    @Override
    public byte[] serialize(Object object) throws Throwable {
        if (object == null) {
            return null;
        }
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            log.error("JDK 序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Throwable {
        if (bytes == null) {
            return null;
        }
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            log.error("JDK 反序列化对象异常，异常信息为: {}", e);
            throw e;
        }
        return (T) obj;
    }
}
