package com.example.cacheinspring.config;

import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Profile("redis")
@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)
public class MyRedisCacheConfiguration extends CachingConfigurerSupport {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

//    @Bean
//    public RedissonSpringCacheManager cacheManager() {
//
////        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
////
////        Map<String, MySpringProperties.Cache.MySentinel.CacheSettingsModel> cacheConfigMap = mySpringProperties.getCache().getSentinel().getCacheConfigAsMap();
////        Map<String, String> appCacheMap = mySpringProperties.getCache().getSentinel().getAppCacheMap();
////
////        appCacheMap.forEach((key, value) -> cacheConfigs.put(key, buildRedisCacheConfig(cacheConfigMap.get(value))));
//
//        return new RedissonSpringCacheManager();
//    }

//    @Bean
//    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(MySpringProperties mySpringProperties) {
//        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();
//
//        URI redisUri = URI.create(mySpringProperties.getCache().getRedis().getServer()[0]);
//
//        Config config = new Config();
//        if (mySpringProperties.getCache().getRedis().isCluster()) {
//            ClusterServersConfig clusterServersConfig = config
//                .useClusterServers()
//                .setMasterConnectionPoolSize(mySpringProperties.getCache().getRedis().getConnectionPoolSize())
//                .setMasterConnectionMinimumIdleSize(mySpringProperties.getCache().getRedis().getConnectionMinimumIdleSize())
//                .setSubscriptionConnectionPoolSize(mySpringProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
//                .addNodeAddress(mySpringProperties.getCache().getRedis().getServer());
//
//            if (redisUri.getUserInfo() != null) {
//                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
//            }
//        } else {
//            SingleServerConfig singleServerConfig = config
//                .useSingleServer()
//                .setConnectionPoolSize(mySpringProperties.getCache().getRedis().getConnectionPoolSize())
//                .setConnectionMinimumIdleSize(mySpringProperties.getCache().getRedis().getConnectionMinimumIdleSize())
//                .setSubscriptionConnectionPoolSize(mySpringProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
//                .setAddress(mySpringProperties.getCache().getRedis().getServer()[0]);
//
//            if (redisUri.getUserInfo() != null) {
//                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
//            }
//        }
//        jcacheConfig.setStatisticsEnabled(true);
//        jcacheConfig.setExpiryPolicyFactory(
//            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, mySpringProperties.getCache().getRedis().getExpiration()))
//        );
//        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
//    }

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
        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

//    @Bean
//    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
//        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
//    }

//    @Bean
//    public JCacheManagerCustomizer cacheManagerCustomizer(CacheConfig cacheConfig) {
//        return cm -> {
////            createCache(cm, com.example.cacheinspring.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
////            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
//            createCache(cm, com.example.cacheinspring.domain.Book.class.getName(), cacheConfig);
//            createCache(cm, Contants.CACHE_PREFIX + "AA", cacheConfig);
////            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
////            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
//            // jhipster-needle-redis-add-entry
//        };
//    }

//    @Bean
//    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
//        return cm -> {
////            createCache(cm, com.example.cacheinspring.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
////            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
//            createCache(cm, com.example.cacheinspring.domain.Book.class.getName(), jcacheConfiguration);
//            createCache(cm, Contants.CACHE_PREFIX + "AA", jcacheConfiguration);
////            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
////            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
//            // jhipster-needle-redis-add-entry
//        };
//    }
//    private void createCache(
//            javax.cache.CacheManager cm,
//            String cacheName,
//            CacheConfig cacheConfig
//    ) {
//        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
//        if (cache != null) {
//            cache.clear();
//        } else {
//            cm.createCache(cacheName, cacheConfig);
//        }
//    }

//    private void createCache(
//        javax.cache.CacheManager cm,
//        String cacheName,
//        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
//    ) {
//        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
//        if (cache != null) {
//            cache.clear();
//        } else {
//            cm.createCache(cacheName, jcacheConfiguration);
//        }
//    }

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
        return new CachingConfigUtils().classNameAndFunctionNameKeyGenerator();
    }

}
