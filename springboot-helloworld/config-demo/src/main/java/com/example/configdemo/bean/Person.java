package com.example.configdemo.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Person {
    private String name;
    private Integer age;
}
