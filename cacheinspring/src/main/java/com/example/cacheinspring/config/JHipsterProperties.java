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

        public Hazelcast getHazelcast() {
            return hazelcast;
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
    }
}
