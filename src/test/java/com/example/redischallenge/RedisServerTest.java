package com.example.redischallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedisServer.class)
@RunWith(SpringJUnit4ClassRunner.class)
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
        redisServer.set("key-0", "value-0", 2);
        redisServer.set("key-1", "value-1", 3);
        redisServer.set("key-2", "value-2", 4);
        redisServer.set("key-3", "value-3", 10);
        redisServer.set("key-4", "value-4", 10);

        assertEquals("value-0", redisServer.get("key-0"));
        assertEquals("value-1", redisServer.get("key-1"));
        assertEquals("value-2", redisServer.get("key-2"));
        assertEquals("value-3", redisServer.get("key-3"));
        assertEquals("value-4", redisServer.get("key-4"));
        TimeUnit.MILLISECONDS.sleep(5000);
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
        Integer count = redisServer.zadd(hashMap, "subset");
        assertEquals(3, count);

        HashMap<String, Integer> hashMap2 = new HashMap<>();
        hashMap2.put("A", 3);
        hashMap2.put("B", 2);
        hashMap2.put("C", 1);
        hashMap2.put("D", 0);
        Integer count2 = redisServer.zadd(hashMap2, "subset");
        assertEquals(1, count2);
    }

    @Test
    void zcard() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 3);
        hashMap.put("B", 2);
        hashMap.put("C", 1);
        Integer count = redisServer.zadd(hashMap, "subset");
        assertEquals(3, count);

        Integer count2 = redisServer.zcard("subset");
        assertEquals(3, count2);
    }

    @Test
    void zrank() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 3);
        hashMap.put("B", 2);
        hashMap.put("C", 1);
        hashMap.put("D", 1);
        hashMap.put("E", 1);
        Integer count = redisServer.zadd(hashMap, "subset");
        assertEquals(5, count);
        assertEquals(2, redisServer.zrank( "subset", "B"));
        assertEquals(3, redisServer.zrank( "subset", "A"));
        assertEquals(1, redisServer.zrank( "subset", "E"));
        assertEquals(1, redisServer.zrank( "subset", "D"));
        assertEquals(1, redisServer.zrank( "subset", "C"));
    }

    @Test
    void zrange() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 3);
        hashMap.put("B", 2);
        hashMap.put("E", 1);
        hashMap.put("C", 1);
        hashMap.put("D", 1);
        redisServer.zadd(hashMap, "subset");

        Set<String> setList = (Set<String>) redisServer.zrange("subset", 0, 3, null);
        assertEquals(Arrays.asList("C", "D", "E").toString(), setList.toString());

        Map<String, Integer> mapList = (Map<String, Integer>) redisServer.zrange("subset", 3, 5, "WITHSCORES");
        assertEquals("{B=2, A=3}", mapList.toString());
    }

    @Test
    void zrangeWhenNegativeIndex() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 3);
        hashMap.put("B", 2);
        hashMap.put("C", 1);
        hashMap.put("D", 1);
        hashMap.put("E", 1);
        redisServer.zadd(hashMap, "subset");

        Set<String> setList = (Set<String>) redisServer.zrange("subset", 0, -1, null);
        assertEquals(Arrays.asList("C", "D", "E", "B", "A").toString(), setList.toString());

        Set<String> setList2 = (Set<String>) redisServer.zrange("subset", -2, -1, null);
        assertEquals(Arrays.asList("B", "A").toString(), setList2.toString());
    }

}