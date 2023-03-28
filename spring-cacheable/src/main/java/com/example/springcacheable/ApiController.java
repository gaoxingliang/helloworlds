package com.example.springcacheable;

import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class ApiController {

    @Cacheable(value = RedisCache.CACHE_NAME_EXAMPLE, key = "{#root.methodName, #name}")
    @GetMapping("/test")
    public String get(String name) {
        System.out.println("request - " + name);
        return "Hello " + name;
    }

    @NegativeCacheable(value = RedisCache.CACHE_NAME_EXAMPLE, key = "{#root.methodName, #name}")
    @GetMapping("/test2")
    public String get2(String name, boolean fail) {
        if (fail) {
            System.out.println("I will fail");
            int j = 1/0;
        }
        System.out.println("request from negative cache - " + name);
        return "Hello " + name;
    }
}
