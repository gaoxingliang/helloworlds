package com.example.demo.ctrl;

import com.example.demo.pojo.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingCtrl {


    @GetMapping("/greet")
    public Greeting greet(@RequestParam(value = "name", defaultValue = "world") String name) {
        return Greeting.builder().id(System.currentTimeMillis()).content("hello " + name).build();
    }
}
