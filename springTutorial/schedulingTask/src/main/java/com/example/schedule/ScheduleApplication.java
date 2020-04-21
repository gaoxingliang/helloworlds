package com.example.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 激活不同的profile的方式
 * java -jar xxx.jar --spring-profiles.active=dev
 *
 * 覆盖已有的变量值:
 *  java -jar xxx.jar --spring.profiles.active=dev --hello.message="rewrite"
 *
 */
@SpringBootApplication
@EnableScheduling
public class ScheduleApplication {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleApplication.class);

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ScheduleApplication.class, args);
        Obj o = (Obj)ac.getBean("obj");
        System.out.println("Current message is:" + o.helloMessage);
        logger.info("I'm here");
    }

}
