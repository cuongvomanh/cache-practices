package com.example.cacheinspring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConfigurationProperties( prefix="myspring")
public class MySpringProperties {
    private Cache cache = new Cache();
    public Cache getCache() {
        return cache;
    }

    @Component
    public static class Cache {
        private String prefix;
        private Hazelcast hazelcast = new Hazelcast();
        private Redis redis = new Redis();
        private MySentinel sentinel = new MySentinel();

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public Hazelcast getHazelcast() {
            return hazelcast;
        }

        public Redis getRedis() {
            return redis;
        }

        public MySentinel getSentinel() {
            return sentinel;
        }

        public static class Hazelcast {
            private int backupCount;
            private int timeToLiveSeconds;

            public int getBackupCount() {
                return backupCount;
            }

            public void setBackupCount(int backupCount) {
                this.backupCount = backupCount;
            }

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }
        }

        public static class Redis {
            private String[] server;
            private boolean isCluster;
            private int connectionPoolSize;
            private int connectionMinimumIdleSize;
            private int subscriptionConnectionPoolSize;
            private long expiration;

            public String[] getServer() {
                return server;
            }

            public void setServer(String[] server) {
                this.server = server;
            }

            public boolean isCluster() {
                return isCluster;
            }

            public void setCluster(boolean cluster) {
                isCluster = cluster;
            }

            public int getConnectionPoolSize() {
                return connectionPoolSize;
            }

            public void setConnectionPoolSize(int connectionPoolSize) {
                this.connectionPoolSize = connectionPoolSize;
            }

            public int getConnectionMinimumIdleSize() {
                return connectionMinimumIdleSize;
            }

            public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
                this.connectionMinimumIdleSize = connectionMinimumIdleSize;
            }

            public int getSubscriptionConnectionPoolSize() {
                return subscriptionConnectionPoolSize;
            }

            public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
                this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
            }

            public long getExpiration() {
                return expiration;
            }

            public void setExpiration(long expiration) {
                this.expiration = expiration;
            }
        }

        public static class MySentinel {
            private String master;
            private List<String> nodes;
            private int port;
            private String password;
            private Map<String, String> appCacheMap;
            private Map<String, CacheSettingsModel> cacheConfigAsMap;

            public String getMaster() {
                return master;
            }

            public void setMaster(String master) {
                this.master = master;
            }

            public List<String> getNodes() {
                return nodes;
            }

            public void setNodes(List<String> nodes) {
                this.nodes = nodes;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Map<String, String> getAppCacheMap() {
                return appCacheMap;
            }

            public void setAppCacheMap(Map<String, String> appCacheMap) {
                this.appCacheMap = appCacheMap;
            }

            public Map<String, CacheSettingsModel> getCacheConfigAsMap() {
                return cacheConfigAsMap;
            }

            public void setCacheConfigAsMap(Map<String, CacheSettingsModel> cacheConfigAsMap) {
                this.cacheConfigAsMap = cacheConfigAsMap;
            }

            public static class CacheSettingsModel {
                private String timeToLiveSeconds = "1000";

                public String getTimeToLiveSeconds() {
                    return timeToLiveSeconds;
                }
            }
        }
    }
}
