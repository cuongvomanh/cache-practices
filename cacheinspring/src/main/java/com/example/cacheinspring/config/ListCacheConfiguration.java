package com.example.cacheinspring.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Configuration
@EnableCaching
@Profile("listcache")
public class ListCacheConfiguration {
    public static class ListCacheManager extends AbstractCacheManager{

        private Collection<? extends Cache> caches;
        private Map<String, Cache> cacheMap = new HashMap<>();

        @Override
        protected Collection<? extends Cache> loadCaches() {
            return caches;
        }

        @Override
        public Cache getCache(String name){
            if (cacheMap.containsKey(name)){
                return cacheMap.get(name);
            } else {
                return new ListCache(name);
            }
        }
    }
    public static class ListCache implements Cache {
        private String name;
        private Map<String, Object> cacheItems;

        public ListCache(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object getNativeCache() {
            return cacheItems;
        }

        @Override
        public ValueWrapper get(Object key) {
            return new SimpleValueWrapper(cacheItems.get(key));
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            return (T) cacheItems.get(key);
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return (T) cacheItems.get(key);
        }

        @Override
        public void put(Object key, Object value) {
            cacheItems.put(key.toString(), value);
        }

        @Override
        public void evict(Object key) {
            cacheItems.remove(key);
        }

        @Override
        public void clear() {
            cacheItems.clear();
        }
    }
}
