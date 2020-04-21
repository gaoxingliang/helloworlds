package com.example.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Obj {
    @Value("${hello.message}")
    public String helloMessage;



//    @Bean
//    public Res getRes() {
//
//    }
//
//
//    static class Res {
//
//    }
}