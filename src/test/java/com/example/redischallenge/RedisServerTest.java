package com.example.redischallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
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
        redisServer.set("key-0", "value-0", 500);
        redisServer.set("key-1", "value-1", 600);
        redisServer.set("key-2", "value-2", 700);
        redisServer.set("key-3", "value-3", 2000);
        redisServer.set("key-4", "value-4", 3000);

        assertEquals("value-0", redisServer.get("key-0"));
        assertEquals("value-1", redisServer.get("key-1"));
        assertEquals("value-2", redisServer.get("key-2"));
        assertEquals("value-3", redisServer.get("key-3"));
        assertEquals("value-4", redisServer.get("key-4"));
        TimeUnit.MILLISECONDS.sleep(1000);
        assertEquals(null, redisServer.get("key-0"));
        assertEquals(null, redisServer.get("key-1"));
        assertEquals(null, redisServer.get("key-2"));
        assertEquals("value-3", redisServer.get("key-3"));
        assertEquals("value-4", redisServer.get("key-4"));

    }

    @Test
    void setoverwritten() {
        String result = redisServer.set("key", 10);
        result = redisServer.set("key", 20);
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
        Integer count = redisServer.del(Arrays.asList("key1", "key2"));
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


}