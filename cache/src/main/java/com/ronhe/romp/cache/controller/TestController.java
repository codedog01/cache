package com.ronhe.romp.cache.controller;

import com.ronhe.romp.cache.util.SpringContextUtil;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/11/8
 */
@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping("/test")
    public void test() {

    }
}
