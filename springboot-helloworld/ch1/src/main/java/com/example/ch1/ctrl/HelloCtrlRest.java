package com.example.ch1.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;



@RestController
public class HelloCtrlRest {


    @RequestMapping("/hellorest")
    String home() {
        return "hello world rest , now is - " + new Date();
    }
}
