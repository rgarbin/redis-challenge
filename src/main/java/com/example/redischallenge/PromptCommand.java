package com.example.redischallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;


@ShellComponent
public class PromptCommand {

    @Autowired RedisServer redis;

    @ShellMethod("Setting a new key ")
    public void set(String key, String value) {
        redis.set(key, value);
    }

    @ShellMethod("Getting a key")
    public String get(String key) {
        return redis.get(key);
    }

    @ShellMethod("Removing  keys")
    public void del(List<String> keys) {
        for (String key : keys) {
            redis.del(key);
        }
    }

}
