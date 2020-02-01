package com.example.ch1.ctrl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@EnableAutoConfiguration
public class HelloCtrl {


    @ResponseBody
    @RequestMapping("/hello")
    String home() {
        return "hello world , now is - " + new Date();
    }
}
