package com.ronhe.romp.cache.api;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoBufUtil {
    public ProtoBufUtil() {
    }

    public static <T> byte[] serializer(T o) {
//        Schema schema = RuntimeSchema.getSchema(o.getClass());
//        return ProtostuffIOUtil.toByteArray(o, schema, LinkedBuffer.allocate(256));
        return KryoUtil.serialize(o);
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {

//        T obj = null;
//        try {
//            obj = clazz.newInstance();
//            Schema schema = RuntimeSchema.getSchema(obj.getClass());
//            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return obj;
        return (T) KryoUtil.deserialize(bytes);
    }
}