package com.example.springcacheable;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.*;

import java.time.*;

@Configuration
public class RedisCache {

    public static final String CACHE_NAME_EXAMPLE = "example";

    @Autowired
    @Bean
    public RedisCacheManager.RedisCacheManagerBuilder customizer(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration exampleCache = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .withCacheConfiguration("example", exampleCache);
    }
}
