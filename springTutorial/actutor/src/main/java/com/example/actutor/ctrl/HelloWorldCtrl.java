package com.example.actutor.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldCtrl {

    @GetMapping("/api/id")
    public Map get() {
        return new HashMap();
    }
}
