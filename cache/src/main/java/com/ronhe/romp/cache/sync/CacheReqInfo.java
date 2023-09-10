package com.ronhe.romp.cache.sync;

/**
 * 缓存请求信息
 *
 * @author hss
 */
public class CacheReqInfo {
    private String cacheName;

    private Integer operatorType;

    private byte[] cacheKey;

    private byte[] cacheVal;

    private String cacheKeyClassName;

    private String cacheValClassName;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public byte[] getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(byte[] cacheKey) {
        this.cacheKey = cacheKey;
    }

    public byte[] getCacheVal() {
        return cacheVal;
    }

    public void setCacheVal(byte[] cacheVal) {
        this.cacheVal = cacheVal;
    }

    public String getCacheKeyClassName() {
        return cacheKeyClassName;
    }

    public void setCacheKeyClassName(String cacheKeyClassName) {
        this.cacheKeyClassName = cacheKeyClassName;
    }

    public String getCacheValClassName() {
        return cacheValClassName;
    }

    public void setCacheValClassName(String cacheValClassName) {
        this.cacheValClassName = cacheValClassName;
    }
}

