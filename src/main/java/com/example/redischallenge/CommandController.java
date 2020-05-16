package com.example.redischallenge;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
        String[] delList = new String[1];
        delList[0] = key;
        return redisServer.del(delList);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cmd/dbsize")
    Object dbsize() {
        return redisServer.dbsize();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cmd/incr/{key}")
    Integer incr(@PathVariable(value = "key") String key) {
        return redisServer.incr(key);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/cmd/zadd/{set}")
    Integer zadd(@PathVariable(value = "set") String set, @RequestBody Map<String, Integer> map) {
        return redisServer.zadd(map, set);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cmd/zcard/{set}")
    Integer zcard(@PathVariable(value = "set") String set) {
        return redisServer.zcard(set);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cmd/zrank/{set}/{key}")
    Integer zrank(@PathVariable(value = "set") String set, @PathVariable(value = "key") String key) {
        return redisServer.zrank(set, key);
    }
}
