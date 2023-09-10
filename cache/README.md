# ROMP-CACHE
> 背景：我们知道一个程序的瓶颈在于数据库，内存的速度远远大于硬盘的速度，当我们一次又一次请求数据库或远程服务时会导致大量的时间耗费在数据库操作或远程方法调用上，以致于程序性能恶化，因此我们需要将数据缓存在内存一份，访问时先读取内存中的数据。
> 为了解决该问题，我们设计和开发了 ROMP-CACHE 缓存框架

## 核心功能
1. 可以支持不同的缓存实现，如 Ehcache/Memcached/Redis 等，只需增加其实现即可完成集成
2. 可以在缓存操作的前后根据需求增加不同的功能，如 缓存操作的前后打印日志、缓存统计、缓存访问记录、缓存同步等，只需增加功能实现即可完成集成
3. 可以单体使用，也可以和Spring集成。增加的额外功能可以动态的配置，如 只需要缓存统计，不需要缓存同步，在配置文件中就可以去除缓存统计而无需改动代码

## 缓存框架使用说明
### 准备操作
- 引入相关依赖（ROMP-CACHE）
```
<dependency>
	<groupId>com.ronhe.romp</groupId>
	<artifactId>romp-cache</artifactId>
	<version>8.0.0</version>
</dependency>
```

- 开启缓存相关接口白名单
```
<util:map id="mapping">
    ...
	<entry key="/romp/cache/*.do" value="anon"></entry>
    ...
</util:map>
```

- 增加缓存配置文件romp-cache.yml文件，文件内容如下
<br> resources/cache/romp-cache.yml 文件内容示例
```
cache:
  type:
    active: ehcache
  listener:
    enable: true
  sync:
    connectTimeout: 3000
    readTimeout: 3000
    period: 100
    maxPeriod: 1000
    attempts: 2
    listOfServers: http://127.0.0.1:9000, http://127.0.0.1:9001
```
属性说明：
1. cache.type.active        激活的缓存类型
2. cache.listener.enable    是否开启缓存监听

以下属性只有在开启缓存同步功能情况下有效
3. cache.sync.connectTimeout    缓存同步连接超时时间（有默认值，可不配置）
4. cache.sync.readTimeout       缓存同步读取数据超时时间（有默认值，可不配置）
5. cache.sync.period            客户端发送同步请求重试周期（单位：毫秒，有默认值，可不配置）
6. cache.sync.period            客户端发送同步请求最大重试周期（单位：毫秒，有默认值，可不配置）
7. cache.sync.attempts          客户端发送同步请求重试次数（单位：毫秒，有默认值，可不配置）
8. cache.sync.listOfServers     接收同步请求的服务列表

### 1. 以工具类的方式使用
- 使用方式如下（示例）
```
public static void main(String[] args) throws Exception {
    // 以工具类的方式使用
    CacheUtil.init();
    CacheUtil.put("user", "name:110", "张三");
    Thread.sleep(1000);
    CacheUtil.get("user", "name:110");
    System.out.println(cacheProvider.getCacheManager());
}
```

### 2. 以Spring注入的方式使用
- 增加 romp-cache.xml 配置注册缓存管理器
<br> resources/cache/romp-cache.xml 文件内容如下
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:cache="http://www.springframework.org/schema/cache"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/cache
        http://www.springframework.org/schema/cache/spring-cache.xsd ">

    <!-- 缓存配置 -->
    <bean id="cacheConfig" class="com.ronhe.romp.cache.config.CacheConfig">
        <property name="activeCacheType" value="ehcache"/>
        <property name="enableCacheListener" value="true"/>
    </bean>

    <!-- 缓存监听1 -->
    <bean id="cacheListener1" class="com.ronhe.romp.cache.listener.DefaultCacheListener">
    </bean>

    <!-- 缓存监听2 -->
    <bean id="cacheListener2" class="com.ronhe.romp.cache.listener.SyncCacheListener">
    </bean>

    <!-- 复合缓存监听 -->
    <bean id="cacheListener" class="com.ronhe.romp.cache.listener.MultiCacheListener">
        <constructor-arg name="cacheListenerList">
            <list>
                <ref bean="cacheListener1"/>
                <ref bean="cacheListener2"/>
            </list>
        </constructor-arg>
    </bean>

    <!-- 缓存工厂 -->
    <bean id="cacheFactory" class="com.ronhe.romp.cache.factory.DefaultCacheFactory" init-method="loadCacheProvider">
        <property name="cacheListener" ref="cacheListener"/>
    </bean>

    <!-- 缓存管理器 -->
    <bean id="cacheManager" class="com.ronhe.romp.cache.spring.DefaultCacheManagerFactoryBean">
        <constructor-arg name="cacheFactory" ref="cacheFactory"/>
        <constructor-arg name="cacheConfig" ref="cacheConfig"/>
    </bean>
</beans>
```
- 使用方式示例如下（示例）
```
public static void main(String[] args) throws Exception {
    // 以Spring配置文件的方式使用    
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:romp-cache.xml");
    CacheManager cacheManager = applicationContext.getBean("cacheManager", CacheManager.class);
    System.out.println(cacheManager);
    Cache cache = cacheManager.getCache("cache");
    cache.put("name","张三");
    Cache.ValueWrapper nameValue = cache.get("name");
    System.out.println(nameValue.get());
}
```

## 说明
- cacheManager 为 Spring 提供的缓存管理器，全限定名为 org.springframework.cache.CacheManager，通过该对象的 getCache(String cacheName) 方法可以获取到对应的缓存
- Cache 为 Spring 提供的缓存对象，全限定名为 org.springframework.cache.Cache，通过该对象可以进行缓存的操作

- 项目以SPI的方式进行多种类型缓存的注册，具体类型可以通过resources/META-INF/services/com.ronhe.romp.cache.provider.CacheProvider 文件进行变更
<br> com.ronhe.romp.cache.provider.CacheProvider文件内容如下，如增加了新的实现，可换行增加新的实现类的全限定名
```
com.ronhe.romp.cache.provider.ehcache.EhcacheProvider
com.ronhe.romp.cache.provider.ehcache.RedisProvider
```
