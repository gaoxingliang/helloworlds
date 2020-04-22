package com.example.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Obj {
    @Value("${hello.message}")
    public String helloMessage;


    @JsonIgnore
    public String worldMessage;


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