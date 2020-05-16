package com.example.redischallenge;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class RedisServer {

    private TimeoutMap<String, Object> redis;
    private TreeMap<String, Integer> treeMap;

    public RedisServer() {
        redis = new TimeoutMap<>();
        treeMap = new TreeMap<String, Integer>();
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

    public Integer del(String[] args) {
        Integer count = 0;

        for (int i = 0; i < args.length; i++) {
            if (redis.containsKey(args[i])) {
                redis.remove(args[i]);
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

    public Integer zadd(Map<String, Integer> subset, String subsetName) {

        Integer count = 0;
        for (Map.Entry<String, Integer> entry : subset.entrySet()) {
            if (!treeMap.containsKey(subsetName + "_" + entry.getKey())) {
                count++;
            }
            treeMap.put(subsetName + "_" + entry.getKey(), entry.getValue());
        }

        return count;
    }

    public Integer zcard(String subsetName) {
        TreeMap<String, Integer> stringIntegerMap = (TreeMap<String, Integer>) TreeMapComparator.sortByValues(treeMap);
        Map<String, Integer> result =  getSubset(subsetName, stringIntegerMap);

        return result.size();
    }

    public Integer zrank(String subsetName, String key) {
        TreeMap<String, Integer> stringIntegerMap = (TreeMap<String, Integer>) TreeMapComparator.sortByValues(treeMap);
        Map<String, Integer> result =  getSubset(subsetName, stringIntegerMap);

        HashSet<Integer> rankList = new HashSet<>();

        for (Map.Entry<String, Integer> entry : result.entrySet()) {

            rankList.add(entry.getValue());
            if (entry.getKey().equals(key)) {
                break;
            }
        }

        if (rankList.size() == 0) {
            return null;
        }

        return rankList.size();

    }


    private Map<String, Integer> getSubset(String subsetName, TreeMap<String, Integer> stringIntegerMap) {
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();

        for(Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
            if (entry.getKey().startsWith(subsetName)) {
                String str = StringUtils.remove(entry.getKey(), subsetName + "_");
                linkedHashMap.put(str, entry.getValue());
            }
        }

        return linkedHashMap;
    }

}
