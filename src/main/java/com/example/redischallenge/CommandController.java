package com.example.redischallenge;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class CommandController {

    @Autowired
    RedisServer redisServer;

    @RequestMapping(method = RequestMethod.GET, path = "/cmd")
    Object get(@RequestParam(value = "key") String key) {
        return redisServer.get(key);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/cmd/{key}")
    Object set(@PathVariable(value = "key") String key, @RequestBody String value) {
        return redisServer.set(key, value);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cmd/{key}")
    Integer del(@PathVariable(value = "key") String key) {
        return redisServer.del(Arrays.asList(key));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cmd/dbsize")
    Object dbsize() {
        return redisServer.dbsize();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cmd/incr/{key}")
    Integer incr(@PathVariable(value = "key") String key) {
        return redisServer.incr(key);
    }

}