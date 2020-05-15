package com.example.redischallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedisServer.class)
class RedisServerTest {

    RedisServer redisServer;

    @BeforeEach
    public void setup() {
        redisServer = new RedisServer();
    }

    @Test
    void setString() {
        String result = redisServer.set("key", "value");
        assertEquals("OK", result);
    }

    @Test
    void setIntger() {
        String result = redisServer.set("key", 10);
        assertEquals("OK", result);
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