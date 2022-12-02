package com.example.springaop.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @GetMapping("ok")
    public String ok(String u) {
        System.out.println("callling ok in UserController.. " + u);
        return "ok - " + u;
    }

    @ResponseBody
    @GetMapping
    public String error() {
        System.out.println("callling error in UserController.. ");
        int i = 1 / 0;
        return "";
    }
}
