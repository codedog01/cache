package com.ronhe.romp.cache.enums;

/**
 * 缓存操作方法
 *
 * @author hss
 */
public enum CacheOperatorType {
    /**
     * 获取原始缓存对象
     */
    GET_NATIVE_CACHE(-1),

    /**
     * 获取缓存名称
     */
    GET_NAME(0),

    /**
     * 获取 ValueWrapper
     */
    GET_WRAPPER(1),

    /**
     * 获取 Object
     */
    GET(2),

    /**
     * 获取 参数中有 Callable
     */
    GET_CALLABLE(3),

    /**
     * 设置
     */
    PUT(4),

    /**
     * 设置 putIfAbsent在放入数据时,如果存在重复的key,那么putIfAbsent不会放入值
     */
    PUT_IF_ABSENT(5),

    /**
     * 失效
     */
    EVICT(6),

    /**
     * 清空
     */
    CLEAR(7);

    private int code;

    CacheOperatorType(int code) {
        this.code = code;
    }

    public static CacheOperatorType of(int code) {
        for (CacheOperatorType type : CacheOperatorType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
