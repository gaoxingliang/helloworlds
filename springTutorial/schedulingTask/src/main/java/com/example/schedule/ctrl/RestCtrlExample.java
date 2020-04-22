package com.example.schedule.ctrl;

import com.example.schedule.Obj;
import com.example.schedule.exceptionExample.MyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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

    /**
     * 实际返回json:
     * {"helloMessage":"qqq"}
     * 注意worldMessage被ignore了.
     *
     * @param id
     * @return
     */
    @GetMapping("/api/get")
    public ResponseEntity getByid(@RequestParam(name="id") int id) {
        System.out.println("Got an get by param  "+ id);
        Obj x = new Obj();
        x.helloMessage = "qqq";
        x.worldMessage = "thisisWorld";
        // sleep for sometime to check our metrics
        try {
            Thread.sleep(new Random().nextInt(2) * 1000 + 10);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(x);
    }

    @PutMapping("/api/put")
    public void put(@RequestBody Req q) {
        System.out.println("Got an put req  "+ q.username);
        // sleep for sometime to check our metrics
        try {
            Thread.sleep(new Random().nextInt(3) * 1000 + 10);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Req {

        public Req() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        String username, password;
    }


}
