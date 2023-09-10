package com.ronhe.romp.cache.enums;

import com.ronhe.romp.cache.consts.CacheConfigYamlKey;
import com.ronhe.romp.cache.serialize.CacheKvSerializer;
import com.ronhe.romp.cache.serialize.jackson.JacksonCacheKvSerializer;
import com.ronhe.romp.cache.serialize.jdk.JdkCacheKvSerializer;
import com.ronhe.romp.cache.serialize.kryo.KryoCacheKvSerializer;
import com.ronhe.romp.cache.serialize.protostuff.ProtostuffCacheKvSerializer;
import com.ronhe.romp.cache.util.CacheYmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存序列化枚举
 *
 * @author hss
 */

public enum CacheKvSerializerEnum {
    JDK(new JdkCacheKvSerializer()),
    PROTOSTUFF(new ProtostuffCacheKvSerializer()),
    KRYO(new KryoCacheKvSerializer()),
    JACKSON(new JacksonCacheKvSerializer());

    private static Logger log = LoggerFactory.getLogger(CacheKvSerializerEnum.class);

    private CacheKvSerializer cacheKvSerializer;

    private static CacheKvSerializer specifyCacheKvSerializer;

    CacheKvSerializerEnum(CacheKvSerializer cacheKvSerializer) {
        this.cacheKvSerializer = cacheKvSerializer;
    }

    public static CacheKvSerializer getSetupCacheKvSerializer() {
        if (specifyCacheKvSerializer == null) {
            String serializerName = CacheYmlUtil.getPropVal(CacheConfigYamlKey.SYNC_SERIALIZE);
            CacheKvSerializerEnum match = match(serializerName, CacheKvSerializerEnum.JACKSON);
            specifyCacheKvSerializer = match.cacheKvSerializer;
        }
        return specifyCacheKvSerializer;
    }

    public static CacheKvSerializerEnum match(String name, CacheKvSerializerEnum defaultSerializer) {
        CacheKvSerializerEnum[] serializerEnums = values();
        for (CacheKvSerializerEnum serializerEnum : serializerEnums) {
            if (serializerEnum.name().equalsIgnoreCase(name)) {
                log.info("[Cache] 当前使用的序列化方式为: [{}]", serializerEnum.name());
                return serializerEnum;
            }
        }
        log.info("[Cache] 将采用默认的序列化方式为: [{}]", defaultSerializer.name());
        return defaultSerializer;
    }
}
