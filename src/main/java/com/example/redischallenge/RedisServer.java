package com.example.redischallenge;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RedisServer {

    private Map<String, String> redis;

    public RedisServer() {
        redis = new HashMap<>();
    }

    public void set(String key, String value) {
        redis.put(key, value);
    }

    public String get(String key) {
        return redis.get(key);
    }

    public String del(String key) {
        return redis.remove(key);
    }
}
