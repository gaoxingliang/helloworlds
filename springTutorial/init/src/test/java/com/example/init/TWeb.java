package com.example.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TWeb implements ApplicationRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(TWeb.class).run("-h help -u root");
        System.out.println();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("opnames:" + args);
    }
}
