package com.example.cacheinspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableCaching
@Profile("sentinel")
public class RedisSentinelCacheConfiguration extends CachingConfigurerSupport {
    @Autowired
    private MySpringProperties mySpringProperties;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(mySpringProperties.getCache().getSentinel().getMaster());

        mySpringProperties.getCache().getSentinel().getNodes().forEach(s -> sentinelConfig.sentinel(s, Integer.valueOf(mySpringProperties.getCache().getSentinel().getPort())));
        sentinelConfig.setPassword(RedisPassword.of(mySpringProperties.getCache().getSentinel().getPassword()));

        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .disableCachingNullValues();
    }

    private RedisCacheConfiguration buildRedisCacheConfig(MySpringProperties.Cache.Sentinel.CacheConfig cacheConfig) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(Long.parseLong(cacheConfig.getTimeToLiveSeconds())))
                .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager cacheManager() {

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();

        Map<String, MySpringProperties.Cache.Sentinel.CacheConfig> cacheConfigByTypeMap = mySpringProperties.getCache().getSentinel().getCacheConfigByTypeMap();
        Map<String, String> cacheTypeMap = mySpringProperties.getCache().getSentinel().getCacheTypeMap();

        cacheTypeMap.forEach((key, value) -> cacheConfigs.put(key, buildRedisCacheConfig(cacheConfigByTypeMap.get(value))));

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfiguration())
                .withInitialCacheConfigurations(cacheConfigs)
                .transactionAware()
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new CachingConfigUtils().classNameAndFunctionNameAndParamsKeyGenerator();
    }
}
