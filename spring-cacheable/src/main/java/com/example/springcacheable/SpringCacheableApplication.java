package com.example.springcacheable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.*;

@EnableCaching
@SpringBootApplication
public class SpringCacheableApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheableApplication.class, args);
    }

}
