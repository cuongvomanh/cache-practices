package com.example.cacheinspring.config;

import org.springframework.stereotype.Component;

@Component
public class JHipsterProperties {
    private Cache cache = new Cache();
    public Cache getCache() {
        return cache;
    }

    @Component
    public static class Cache {
        private Hazelcast hazelcast = new Hazelcast();
        private Redis redis = new Redis();

        public Hazelcast getHazelcast() {
            return hazelcast;
        }

        public Redis getRedis() {
            return redis;
        }

        public static class Hazelcast {
            private final int backupCount = 1;
            private final int timeToLiveSeconds = 3000;
            public int getBackupCount() {
                return backupCount;
            }

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }
        }

        public static class Redis {
            private String[] server = {"redis://localhost:6379","redis://localhost:16379","redis://localhost:26379"};
            private boolean isCluster=true;
            private int connectionPoolSize=1000;
            private int connectionMinimumIdleSize=1000;
            private int subscriptionConnectionPoolSize=1000;
            private long expiration=1000;

            public String[] getServer() {
                return server;
            }

            public boolean isCluster() {
                return isCluster;
            }

            public int getConnectionPoolSize() {
                return connectionPoolSize;
            }

            public int getConnectionMinimumIdleSize() {
                return connectionMinimumIdleSize;
            }

            public int getSubscriptionConnectionPoolSize() {
                return subscriptionConnectionPoolSize;
            }

            public long getExpiration() {
                return expiration;
            }
        }
    }
}
