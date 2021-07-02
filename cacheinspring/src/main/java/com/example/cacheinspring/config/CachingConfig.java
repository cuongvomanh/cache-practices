package com.example.cacheinspring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class CachingConfig {

//    @Bean
//    public CacheManager cacheManager(){
//        return new ConcurrentMapCacheManager("addresses");
//    }

//    @Bean
//    @Primary
//    public CacheManager cacheManager(){
//        RedisCacheManager.builder(redisConnectionFactory).build();
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
//        return cacheManager;
//    }
//
//    @Bean
//    public JCacheCacheManager jCacheCacheManager() {
//        val cachingProvider = Caching.getCachingProvider()
//        val configLocation = cacheProperties().resolveConfigLocation(cacheProperties().jcache.config)
//        val cacheManager = cachingProvider.getCacheManager(configLocation.uri, classLoader)
//        jCacheManagerCustomizers.orderedStream().forEach { it.customize(cacheManager) }
//        return JCacheCacheManager(cacheManager);
//    }
}
