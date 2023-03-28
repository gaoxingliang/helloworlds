package com.example.springcacheable;

import java.lang.annotation.*;

/**
 * Any class with this annotation will try call original method and
 *  if it failed, it will try to use cache value.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NegativeCacheable {
    /**
     * the cache key. with supports spel
     * @return
     */
    String key();

    /**
     * the cache name.
     * @return
     */
    String value();
}
