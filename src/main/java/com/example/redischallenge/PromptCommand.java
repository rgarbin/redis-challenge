package com.example.redischallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;


@ShellComponent
public class PromptCommand {

    @Autowired RedisServer redis;

    @ShellMethod("Setting a new key with time to expire")
    public String set(String key, String value, @ShellOption(defaultValue = "") Integer seconds) {
        return redis.set(key, value, seconds);
    }

    @ShellMethod("Getting a key-value")
    public Object get(String key) {
        return redis.get(key);
    }

    @ShellMethod("Removing keys")
    public Integer del(List<String> keys) {
        return redis.del(keys);
    }

    @ShellMethod("DB size")
    public Integer dbsize() {
        return redis.dbsize();
    }

    @ShellMethod("Increase keys value")
    public Integer incr(String key) {
        return redis.incr(key);
    }


}
