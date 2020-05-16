package com.example.redischallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedisServer.class)
class RedisServerTest {

    RedisServer redisServer;

    @BeforeEach
    public void setup() {
        redisServer = new RedisServer();
    }

    @Test
    void setWhenString() {
        String result = redisServer.set("key", "value");
        assertEquals("OK", result);
    }

    @Test
    void setWhenInteger() {
        String result = redisServer.set("key", 10);
        assertEquals("OK", result);
    }

    @Test
    void setWhenExpire() throws InterruptedException {
        redisServer.set("key-0", "value-0", 5);
        redisServer.set("key-1", "value-1", 10);
        redisServer.set("key-2", "value-2", 15);
        redisServer.set("key-3", "value-3", 50);
        redisServer.set("key-4", "value-4", 60);

        assertEquals("value-0", redisServer.get("key-0"));
        assertEquals("value-1", redisServer.get("key-1"));
        assertEquals("value-2", redisServer.get("key-2"));
        assertEquals("value-3", redisServer.get("key-3"));
        assertEquals("value-4", redisServer.get("key-4"));
        TimeUnit.MILLISECONDS.sleep(30000);
        assertEquals(null, redisServer.get("key-0"));
        assertEquals(null, redisServer.get("key-1"));
        assertEquals(null, redisServer.get("key-2"));
        assertEquals("value-3", redisServer.get("key-3"));
        assertEquals("value-4", redisServer.get("key-4"));

    }

    @Test
    void setWhenOverwritten() {
        redisServer.set("key", 10);
        String result = redisServer.set("key", 20);
        assertEquals("OK", result);
        assertEquals(20, redisServer.get("key"));
    }

    @Test
    void getWhenKeyNotExists() {
        assertNull(redisServer.get("keyGet"));
    }

    @Test
    void getWhenKeyExists() {
        redisServer.set("exists", "OK");
        assertEquals("OK", redisServer.get("exists"));
    }

    @Test
    void del() {
        redisServer.set("key1", "OK");
        redisServer.set("key2", "OK");
        redisServer.set("key3", "OK");


        String[] delList = new String[3];
        delList[0] = "key1";
        delList[1] = "key2";

        Integer count = redisServer.del(delList);
        assertEquals(2, count);
    }

    @Test
    void dbsize() {
        redisServer.set("key1", "OK");
        redisServer.set("key2", "OK");
        redisServer.set("key3", "OK");
        Integer count = redisServer.dbsize();
        assertEquals(3, count);
    }

    @Test
    void incr() {
        redisServer.set("key1", 2);
        Integer count = redisServer.incr("key1");
        assertEquals(3, count);
    }

    @Test
    void incrWhenKeuNotExists() {
        Integer count = redisServer.incr("key1");
        assertEquals(0, count);
    }

    @Test
    void zadd() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 3);
        hashMap.put("B", 2);
        hashMap.put("C", 1);
        Map<String, Integer> sortedList = redisServer.zadd(hashMap, "subset");

        // Checking key-value
        assertEquals(1, sortedList.get("C"));
        assertEquals(2, sortedList.get("B"));
        assertEquals(3, sortedList.get("A"));

        // Checking order
        assertEquals(Arrays.asList(1, 2, 3).toString(), sortedList.values().toString());

        // Adding new subset

        HashMap<String, Integer> hashMap1 = new HashMap<>();
        hashMap1.put("A", 1);
        hashMap1.put("B", 2);
        hashMap1.put("C", 3);
        hashMap1.put("D", 1);
        Map<String, Integer> sortedList2 = redisServer.zadd(hashMap1, "subset");

        // Checking key-value
        assertEquals(1, sortedList2.get("A"));
        assertEquals(1, sortedList2.get("D"));
        assertEquals(2, sortedList2.get("B"));
        assertEquals(3, sortedList2.get("C"));

        //Checking order
        assertEquals(Arrays.asList(1, 1, 2, 3).toString(), sortedList2.values().toString());


        // Checking second subset
        HashMap<String, Integer> hashMapSubset2 = new HashMap<>();
        hashMapSubset2.put("A", 10);
        hashMapSubset2.put("B", 9);
        hashMapSubset2.put("C", 8);
        Map<String, Integer> sortedListSubSet2 = redisServer.zadd(hashMapSubset2, "subset2");

        // Checking key-value
        assertEquals(8, sortedListSubSet2.get("C"));
        assertEquals(9, sortedListSubSet2.get("B"));
        assertEquals(10, sortedListSubSet2.get("A"));
        assertEquals(Arrays.asList(8, 9, 10).toString(), sortedListSubSet2.values().toString());
    }


}