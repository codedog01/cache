package com.ronhe.romp.cache.provider.redis;

import com.ronhe.romp.cache.api.CacheUtil;
import com.ronhe.romp.cache.config.CacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/11/8
 */
public class RedisConfig {
    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    {

        if (CacheUtil.getCacheConfig().getActiveCacheType().equals("redis")) {
            ClassPathResource xmlCacheConfig = null;
            logger.info("[Cache] 读取【cache/romp-cache-redis.yml】配置");
            xmlCacheConfig = new ClassPathResource("cache/romp-cache-redis.yml");
            YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
            propertiesFactoryBean.setResources(xmlCacheConfig);
            Properties properties = propertiesFactoryBean.getObject();
            Class<?> aClass = this.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                try {
                    declaredField.setAccessible(true);
                    String property = properties.getProperty("redis." + declaredField.getName());
                    if (property == null) {
                        continue;
                    }
                    if ("expires".equals(declaredField.getName())) {
                        HashMap<String, Long> stringLongHashMap = new HashMap<>();
                        String[] split = property.split(",");
                        for (String s : split) {
                            String[] split1 = s.split(":");
                            stringLongHashMap.put(split1[0].trim(), Long.parseLong(split1[1].trim()));
                        }
                        declaredField.set(this, stringLongHashMap);
                    }
                    else if ("java.lang.Integer".equals(declaredField.getType().getTypeName())) {
                        declaredField.set(this, Integer.parseInt(property));
                    } else if ("java.lang.Boolean".equals(declaredField.getType().getTypeName())) {
                        declaredField.set(this, Boolean.parseBoolean(property));
                    } else {
                        declaredField.set(this, property);
                    }
                } catch (Exception e) {
                    logger.error("[Cache] cache/cache-redis.yml配置错误，【redis.{}】", declaredField.getName());
                    throw new RuntimeException("cache/cache-redis.yml配置错误");
                }
            }
        }

    }

    public String model;
    public String singleHost;
    public Integer singlePort;
    public String singlePassword;
    public Integer singleConnectionTimeout;


    public String sentinelMasterName;
    public String sentinelMasterPassword;
    public String sentinelNodes;


    public String clusterNodes;
    public Integer clusterMaxRedirects;
    public String clusterPassword;

    public Integer jedisPoolMaxActive;
    public Integer jedisPoolMaxWait;
    public Integer jedisPoolMaxIdle;
    public Integer jedisPoolMinIdle;


    public Boolean usePrefix=Boolean.FALSE;
    public String cachePrefix = "";
    public Integer defaultExpiration = -1;
    public Map<String, Long> expires=new HashMap<>();
    public Boolean loadRemoteCachesOnStartup = Boolean.FALSE;
    public Boolean transactionAware = Boolean.FALSE;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSingleHost() {
        return singleHost;
    }

    public void setSingleHost(String singleHost) {
        this.singleHost = singleHost;
    }

    public Integer getSinglePort() {
        return singlePort;
    }

    public void setSinglePort(Integer singlePort) {
        this.singlePort = singlePort;
    }

    public String getSinglePassword() {
        return singlePassword;
    }

    public void setSinglePassword(String singlePassword) {
        this.singlePassword = singlePassword;
    }

    public Integer getSingleConnectionTimeout() {
        return singleConnectionTimeout;
    }

    public void setSingleConnectionTimeout(Integer singleConnectionTimeout) {
        this.singleConnectionTimeout = singleConnectionTimeout;
    }

    public String getSentinelMasterName() {
        return sentinelMasterName;
    }

    public void setSentinelMasterName(String sentinelMasterName) {
        this.sentinelMasterName = sentinelMasterName;
    }

    public String getSentinelMasterPassword() {
        return sentinelMasterPassword;
    }

    public void setSentinelMasterPassword(String sentinelMasterPassword) {
        this.sentinelMasterPassword = sentinelMasterPassword;
    }

    public String getSentinelNodes() {
        return sentinelNodes;
    }

    public void setSentinelNodes(String sentinelNodes) {
        this.sentinelNodes = sentinelNodes;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getClusterMaxRedirects() {
        return clusterMaxRedirects;
    }

    public void setClusterMaxRedirects(Integer clusterMaxRedirects) {
        this.clusterMaxRedirects = clusterMaxRedirects;
    }

    public String getClusterPassword() {
        return clusterPassword;
    }

    public void setClusterPassword(String clusterPassword) {
        this.clusterPassword = clusterPassword;
    }

    public Integer getJedisPoolMaxActive() {
        return jedisPoolMaxActive;
    }

    public void setJedisPoolMaxActive(Integer jedisPoolMaxActive) {
        this.jedisPoolMaxActive = jedisPoolMaxActive;
    }

    public Integer getJedisPoolMaxWait() {
        return jedisPoolMaxWait;
    }

    public void setJedisPoolMaxWait(Integer jedisPoolMaxWait) {
        this.jedisPoolMaxWait = jedisPoolMaxWait;
    }

    public Integer getJedisPoolMaxIdle() {
        return jedisPoolMaxIdle;
    }

    public void setJedisPoolMaxIdle(Integer jedisPoolMaxIdle) {
        this.jedisPoolMaxIdle = jedisPoolMaxIdle;
    }

    public Integer getJedisPoolMinIdle() {
        return jedisPoolMinIdle;
    }

    public void setJedisPoolMinIdle(Integer jedisPoolMinIdle) {
        this.jedisPoolMinIdle = jedisPoolMinIdle;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Boolean getUsePrefix() {
        return usePrefix;
    }

    public void setUsePrefix(Boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public Integer getDefaultExpiration() {
        return defaultExpiration;
    }

    public void setDefaultExpiration(Integer defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }

    public Boolean getLoadRemoteCachesOnStartup() {
        return loadRemoteCachesOnStartup;
    }

    public void setLoadRemoteCachesOnStartup(Boolean loadRemoteCachesOnStartup) {
        this.loadRemoteCachesOnStartup = loadRemoteCachesOnStartup;
    }

    public Boolean getTransactionAware() {
        return transactionAware;
    }

    public void setTransactionAware(Boolean transactionAware) {
        this.transactionAware = transactionAware;
    }
}
