package com.example.redischallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ShellComponent
public class PromptCommand {

    @Autowired RedisServer redis;

    @ShellMethod("Setting a new key with time to expire")
    public String set(String key, String value, @ShellOption(defaultValue = "") Integer seconds) {
        return redis.set(key, value, seconds);
    }

    @ShellMethod("Getting a key-value")
    public Object get(String arg) {
        return redis.get(arg);
    }

    @ShellMethod("Removing keys")
    public Integer del(@ShellOption String[] args) {
        return redis.del(args);
    }

    @ShellMethod("DB size")
    public Integer dbsize() {
        return redis.dbsize();
    }

    @ShellMethod("Increase keys value")
    public Integer incr(String key) {
        return redis.incr(key);
    }

    @ShellMethod("Increase keys value")
    public Map zadd( @ShellOption  String subsetName, @ShellOption HashMap<Integer, String> subset) {

        HashMap<String, Integer> adjustedList = new HashMap<>();

        subset.entrySet().stream().forEach(e -> {
            adjustedList.put(e.getValue(), e.getKey());
        });
        
        return redis.zadd(adjustedList, subsetName);
    }

}
