package com.ronhe.romp.cache.sync;

import com.ronhe.romp.cache.consts.CacheConfigYamlKey;
import com.ronhe.romp.cache.util.CacheYmlUtil;
import feign.Feign;
import feign.Request;
import feign.RetryableException;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 缓存同步客户端
 *
 * @author hss
 */
public class SyncCacheClient {
    private final static Logger logger = LoggerFactory.getLogger(SyncCacheClient.class);

    private static CacheSyncConfig cacheSyncConfig;

    static {
        // 从配置文件中加载配置
        Properties properties = CacheYmlUtil.getCacheConfigProperties();

        // 将配置转为 CacheSyncConfig 对象
        cacheSyncConfig = convertProperties(properties);
    }

    /**
     * 根据缓存同步信息同步缓存数据
     *
     * @param syncInfo
     */
    public static void syncCacheData(CacheReqInfo syncInfo) {
        List<String> serverList = cacheSyncConfig.getSyncServerList();
        if (serverList != null && serverList.size() > 0) {
            for (String server : serverList) {
                try {
                    SyncCacheService api = buildSyncCacheServiceFeignClient(server);
                    api.syncCacheData(syncInfo);
                } catch (RetryableException s) {
                    logger.info("连接超时：{}", s.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据缓存请求信息获取缓存数据
     *
     * @param syncInfo
     */
    public static void getCacheData(CacheReqInfo syncInfo) {
        List<String> serverList = cacheSyncConfig.getSyncServerList();
        if (serverList != null && serverList.size() > 0) {
            for (String server : serverList) {
                try {
                    SyncCacheService api = buildSyncCacheServiceFeignClient(server);
                    api.getCacheData(syncInfo);
                } catch (RetryableException s) {
                    logger.info("连接超时：{}", s.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 构建 feign
     *
     * @param serverAddress
     * @return
     */
    private static SyncCacheService buildSyncCacheServiceFeignClient(String serverAddress) {
        //创建feign的构建者
        SyncCacheService syncCacheService = Feign.builder()
                //JSON编码
                .encoder(new JacksonEncoder())
                //JSON解码
                .decoder(new JacksonDecoder())
                //设置超时
                .options(new Request.Options(cacheSyncConfig.getConnectTimeoutMillis(), cacheSyncConfig.getReadTimeoutMillis()))
                //设置重试
                .retryer(new Retryer.Default(cacheSyncConfig.getPeriod(), cacheSyncConfig.getMaxPeriod(), cacheSyncConfig.getMaxAttempts()))
                .target(SyncCacheService.class, serverAddress);
        return syncCacheService;
    }


    /**
     * 将 properties 文件转化为 CacheSysConfig
     *
     * @param properties
     * @return
     */
    public static CacheSyncConfig convertProperties(Properties properties) {
        CacheSyncConfig syncConfig = new CacheSyncConfig();
        syncConfig.setConnectTimeoutMillis(Integer.parseInt(properties.getProperty(CacheConfigYamlKey.SYNC_CONNECT_TIMEOUT, "10000")));
        syncConfig.setReadTimeoutMillis(Integer.parseInt(properties.getProperty(CacheConfigYamlKey.SYNC_READ_TIMEOUT, "60000")));
        syncConfig.setPeriod(Long.parseLong(properties.getProperty(CacheConfigYamlKey.SYNC_PERIOD, "100")));
        syncConfig.setMaxPeriod(Long.parseLong(properties.getProperty(CacheConfigYamlKey.SYNC_MAX_PERIOD, "1000")));
        syncConfig.setMaxAttempts(Integer.parseInt(properties.getProperty(CacheConfigYamlKey.SYNC_MAX_ATTEMPTS, "5")));
        String servers = properties.getProperty(CacheConfigYamlKey.SYNC_LIST_OF_SERVERS);
        if (servers != null) {
            String[] split = servers.split(",");
            List<String> serversList = Arrays.asList(split);
            syncConfig.setSyncServerList(serversList);
        }
        return syncConfig;
    }
}
