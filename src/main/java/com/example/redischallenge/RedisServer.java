package com.example.redischallenge;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisServer {

    private Map<String, Object> redis;

    public RedisServer() {
        redis = new HashMap<>();
    }

    public String set(String key, Object value) {
        return set(key, value, null);
    }

    public String set(String key, Object value, Integer seconds) {
        try {
            redis.put(key, value);
        } catch (Exception e) {
            return "NOK";
        }
        return "OK";
    }

    public Object get(String key) {
        if (redis.containsKey(key)) {
            return redis.get(key);
        }
        return null;
    }

    public Integer del(List<String> keys) {
        Integer count = 0;

        for (String key : keys) {
            if (redis.containsKey(key)) {
                redis.remove(key);
                count++;
            }
        }

        return count;
    }

    public Integer dbsize() {
        return redis.size();
    }

    public Integer incr(String key) {
        if (redis.containsKey(key)) {
            Integer value = (Integer) get(key);
            Integer next = value + 1;
            set(key, next);
            return next;
        }
        set(key, 0);
        return 0;
    }
}
