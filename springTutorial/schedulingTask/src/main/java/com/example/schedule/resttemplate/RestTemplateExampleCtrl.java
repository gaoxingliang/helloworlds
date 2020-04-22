package com.example.schedule.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class RestTemplateExampleCtrl {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/api/template")
    public void example() {
        /**
         *
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> resp = restTemplate.exchange("http://localhost:9901/api/get?id=7",
                HttpMethod.GET, entity, String.class);
        System.out.println("Got response " + resp.getBody());

    }

    @GetMapping("/api/puttemplate")
    public void putexample() {
        /**
         *
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity <String> entity = new HttpEntity<String>("{\"username\":\"fromputexample\", \"password\" : \"pss\"}", headers);

        ResponseEntity<String> resp = restTemplate.exchange("http://localhost:9901/api/put",
                HttpMethod.PUT, entity, String.class);
        System.out.println("Got put response " + resp.getBody());

    }





}
