package com.example.schedule.ctrl;

import com.example.schedule.exceptionExample.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrlExample {

    @GetMapping("/api/{id}")
    public void get(@PathVariable(name="id") int id) {
        System.out.println("Got an id  "+ id);
        if (id > 100) {
            throw new MyException("id > 100");
        }
    }

    @GetMapping("/api/testException")
    public void getException() {
        System.out.println("Test exception enter");
    }

    @GetMapping("/api/get")
    public void getByid(@RequestParam(name="id") int id) {
        System.out.println("Got an get by param  "+ id);
    }

    @PutMapping("/api/put")
    public void put(@RequestBody Req q) {
        System.out.println("Got an put req  "+ q.username);
    }

    static class Req {
        String username, password;
    }


}
