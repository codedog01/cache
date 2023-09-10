package com.ronhe.romp.cache.consts;

/**
 * 缓存配置Yaml文件中的key
 *
 * @author hss
 */
public interface CacheConfigYamlKey {
    String ACTIVE_CACHE_TYPE = "cache.type.active";

    String ENABLE_CACHE_LISTENER = "cache.listener.enable";

    String SYNC_SERIALIZE = "cache.sync.serialize";

    String SYNC_CONNECT_TIMEOUT = "cache.sync.connectTimeout";

    String SYNC_READ_TIMEOUT = "cache.sync.readTimeout";

    String SYNC_PERIOD = "cache.sync.period";

    String SYNC_MAX_PERIOD = "cache.sync.maxPeriod";

    String SYNC_MAX_ATTEMPTS = "cache.sync.attempts";

    String SYNC_LIST_OF_SERVERS = "cache.sync.listOfServers";
}
