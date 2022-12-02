package com.example.springaop.controller.subpackage;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/other")
public class OtherController {

    @ResponseBody
    @RequestMapping("ok")
    public String ok() {
        return "ok from other";
    }
}
