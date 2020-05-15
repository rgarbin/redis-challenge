package com.example.redischallenge;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisServer {

    private TimeoutMap<String, Object> redis;

    public RedisServer() {
        redis = new TimeoutMap<>();
    }

    public String set(String key, Object value) {
        return set(key, value, null);
    }

    public String set(String key, Object value, Integer seconds) {
        try {
            if (seconds == null) {
                redis.put(key, value);
            } else {
                redis.put(key, value, seconds);
            }
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
