package com.example.redischallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


import java.util.HashMap;
import java.util.Map;


@ShellComponent
public class PromptCommand {

    @Autowired RedisServer redis;

    @ShellMethod("Set key to hold the string value")
    public String set(String key, String value, @ShellOption(defaultValue = "") Integer seconds) {
        return redis.set(key, value, seconds);
    }

    @ShellMethod("Get the value of key")
    public Object get(String arg) {
        return redis.get(arg);
    }

    @ShellMethod("Removes the specified keys.")
    public Integer del(@ShellOption(defaultValue = "") String arg1,
                       @ShellOption(defaultValue = "") String arg2,
                       @ShellOption(defaultValue = "") String arg3,
                       @ShellOption(defaultValue = "") String arg4,
                       @ShellOption(defaultValue = "") String arg5) {

        // Spring shell Not support Infinite Arity yet to receive String[]
        // https://docs.spring.io/spring-shell/docs/current-SNAPSHOT/reference/htmlsingle/
        String[] args = new String[5];
        args[0] = arg1;
        args[1] = arg2;
        args[2] = arg3;
        args[3] = arg4;
        args[4] = arg5;

        return redis.del(args);
    }

    @ShellMethod("Return the number of keys in the currently-selected database.")
    public Integer dbsize() {
        return redis.dbsize();
    }

    @ShellMethod("Increments the number stored at key by one.")
    public Integer incr(String key) {
        return redis.incr(key);
    }

    @ShellMethod("Adds all the specified members with the specified scores to the sorted set stored at key")
    public Integer zadd( @ShellOption  String subsetName,
                     @ShellOption(defaultValue = "") Integer arg1,
                     @ShellOption(defaultValue = "") String value1,
                     @ShellOption(defaultValue = "") Integer arg2,
                     @ShellOption(defaultValue = "") String value2,
                     @ShellOption(defaultValue = "") Integer arg3,
                     @ShellOption(defaultValue = "") String value3,
                     @ShellOption(defaultValue = "") Integer arg4,
                     @ShellOption(defaultValue = "") String value4) {


        // Spring shell Not support Infinite Arity yet to receive String[]
        // https://docs.spring.io/spring-shell/docs/current-SNAPSHOT/reference/htmlsingle/
        HashMap<Integer, String> subset = new HashMap<>();
        if (arg1 != null && arg1 instanceof Integer) subset.put(arg1, value1);
        if (arg2 != null && arg2 instanceof Integer) subset.put(arg2, value2);
        if (arg3 != null && arg3 instanceof Integer) subset.put(arg3, value3);
        if (arg4 != null && arg4 instanceof Integer) subset.put(arg4, value4);

        HashMap<String, Integer> adjustedList = new HashMap<>();

        subset.entrySet().stream().forEach(e -> {
            adjustedList.put(e.getValue(), e.getKey());
        });
        
        return redis.zadd(adjustedList, subsetName);
    }

    @ShellMethod("Returns the sorted set cardinality (number of elements) of the sorted set stored at key.")
    public Integer zcard(String key) {
        return redis.zcard(key);
    }

    @ShellMethod("Returns the rank of member in the sorted set stored at key, with the scores ordered from low to high")
    public Integer zrank(String subsetName, String key) {
        return redis.zrank(subsetName, key);
    }

}
