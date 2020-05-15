package com.example.redischallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeoutMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(5);

    public void put(K key, V value, int ms) {
        super.put(key, value);

        EXECUTOR.schedule(() -> {
            super.remove(key);
        }, ms, TimeUnit.SECONDS);
    }

}
