package com.example.redischallenge;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedisServer.class)
class RedisServerTest {

    @InjectMocks
    RedisServer redisServer;

    @Test
    void redis() {
        redisServer.set("key", "value");
        String result = redisServer.get("key");
        assertEquals("value", result);

        redisServer.del("key");
        result = redisServer.get("key");
        assertNull(result);
    }

}