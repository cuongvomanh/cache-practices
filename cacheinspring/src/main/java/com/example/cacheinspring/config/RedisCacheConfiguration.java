package com.example.cacheinspring.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import javax.cache.configuration.MutableConfiguration;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@Profile("redis")
@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    private Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfiguration.class);
    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public RedissonClient jcacheConfiguration(MySpringProperties mySpringProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(mySpringProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (mySpringProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                    .useClusterServers()
                    .setMasterConnectionPoolSize(mySpringProperties.getCache().getRedis().getConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(mySpringProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(mySpringProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .addNodeAddress(mySpringProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                    .useSingleServer()
                    .setConnectionPoolSize(mySpringProperties.getCache().getRedis().getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(mySpringProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(mySpringProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .setAddress(mySpringProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        config.put(Contants.SERVICE_ONE_CACHE, new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }


    @Bean
    public KeyGenerator keyGenerator() {
        return new CachingConfigUtils().simpleClassNameAndTenantIdAndFunctionNameAndParamsKeyGenerator();
    }

}
