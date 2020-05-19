package com.example.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {

    @Bean
    public HelloWorld Xatasource() {
        HelloWorld x = new HelloWorld();
        x.hello = "x";
        x.world = "q";
        return x;
    }

}
