package com.ronhe.romp.cache.provider.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/11/4
 */
public class RedisConfiguration {

    Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    RedisConfig redisConfig = new RedisConfig();

    private static RedisCacheManager redisCacheManager;

    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = null;
        if (StringUtils.isEmpty(redisConfig.getModel()) || !"single|sentinel|cluster".contains(redisConfig.getModel())) {
            logger.error("[Cache] 当前启用缓存【redis】,请正确配置【romp.cache.redis.model】");
            throw new RuntimeException();
        }
        logger.info("[Cache] 当前启用缓存【redis】,模式为{}】", redisConfig.getModel());

        //手动配置 JedisPoolConfig、RedisClusterConfiguration、JedisConnectionFactory
        if ("single".equals(redisConfig.getModel())) {
            //单机
            JedisShardInfo jedisShardInfo = new JedisShardInfo(redisConfig.getSingleHost(), redisConfig.getSinglePort());
            if (!StringUtils.isEmpty(redisConfig.getSinglePassword())) {
                jedisShardInfo.setPassword(redisConfig.getSinglePassword());
            }
            jedisShardInfo.setConnectionTimeout(redisConfig.getSingleConnectionTimeout());
            jedisConnectionFactory = new JedisConnectionFactory(jedisShardInfo);
        } else if ("sentinel".equals(redisConfig.getModel())) {

            //哨兵模式
            Set<RedisNode> sentinels = new HashSet<RedisNode>();
            for (String sentinelNode : redisConfig.getSentinelNodes().split(",")) {
                String[] split = sentinelNode.split(":");
                sentinels.add(new RedisNode(split[0], Integer.parseInt(split[1].trim())));
            }

            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
            redisSentinelConfiguration.setMaster(redisConfig.getSentinelMasterName());
            redisSentinelConfiguration.setSentinels(sentinels);
            jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, getPoolConfig());
            if (!StringUtils.isEmpty(redisConfig.getSentinelMasterPassword())) {
                jedisConnectionFactory.setPassword(redisConfig.getSentinelMasterPassword());
            }

        } else if ("cluster".equals(redisConfig.getModel())) {
            // cluster 集群模式
            String[] split = redisConfig.getClusterNodes().split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(Arrays.asList(split));
            redisClusterConfiguration.setMaxRedirects(redisConfig.getClusterMaxRedirects());
            jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, getPoolConfig());

            if (!StringUtils.isEmpty(redisConfig.getClusterPassword())) {
                jedisConnectionFactory.setPassword(redisConfig.getClusterPassword());
            }
            jedisConnectionFactory.setTimeout(15000);
            jedisConnectionFactory.setPort(4566);
        }
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    private RedisTemplate<String, Object> redisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = jedisConnectionFactory();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认的jdkSerializeable序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    //    @Bean
//    @Conditional(BeanCondition.RedisCondition.class)
    public RedisCacheManager redisCacheManager() {
        if (redisCacheManager == null) {
            synchronized (RedisConfiguration.class) {
                if (redisCacheManager == null) {
                    redisCacheManager = new RedisCacheManager(redisTemplate());
//                    DefaultRedisCachePrefix defaultRedisCachePrefix = new DefaultRedisCachePrefix();
//                    defaultRedisCachePrefix.prefix(redisConfig.cachePrefix);
////                    ArrayList<String> objects = new ArrayList<>();
////                    objects.add("cache");
////                    objects.add("cache1");
////                    objects.add("cache2");
////                    redisCacheManager.setCacheNames(objects);
//                    redisCacheManager.setCachePrefix(defaultRedisCachePrefix);
//                    redisCacheManager.setDefaultExpiration(redisConfig.defaultExpiration);
//                    redisCacheManager.setExpires(redisConfig.expires);
//                    redisCacheManager.setUsePrefix(redisConfig.usePrefix);
//                    redisCacheManager.setTransactionAware(redisConfig.transactionAware);
//                    redisCacheManager.setLoadRemoteCachesOnStartup(redisConfig.loadRemoteCachesOnStartup);
                    redisCacheManager.afterPropertiesSet();
                }
            }
        }
//
        return redisCacheManager;
    }


    private JedisPoolConfig getPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfig.getJedisPoolMaxActive());
        poolConfig.setMaxIdle(redisConfig.getJedisPoolMaxIdle());
        poolConfig.setMaxWaitMillis(redisConfig.getJedisPoolMaxWait());
        poolConfig.setMinIdle(redisConfig.getJedisPoolMinIdle());
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(true);
        return poolConfig;
    }


}
