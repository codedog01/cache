package com.ronhe.romp.cache.sync;

import java.util.List;

/**
 * 缓存同步配置
 *
 * @author hss
 */
public class CacheSyncConfig {
    private int connectTimeoutMillis;

    private int readTimeoutMillis;

    private long period;

    private long maxPeriod;

    private int maxAttempts;

    private List<String> syncServerList;

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public int getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public void setReadTimeoutMillis(int readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public long getMaxPeriod() {
        return maxPeriod;
    }

    public void setMaxPeriod(long maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public List<String> getSyncServerList() {
        return syncServerList;
    }

    public void setSyncServerList(List<String> syncServerList) {
        this.syncServerList = syncServerList;
    }
}
