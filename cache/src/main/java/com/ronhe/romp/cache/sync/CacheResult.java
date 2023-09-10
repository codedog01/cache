package com.ronhe.romp.cache.sync;

/**
 * 缓存同步结果
 *
 * @author hss
 */
public class CacheResult {
    private String code = "0000";
    private String msg = "操作成功";
    private Object body;

    public CacheResult(String code) {
        this.code = code;
    }

    public CacheResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CacheResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}