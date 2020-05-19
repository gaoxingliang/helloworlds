package com.example.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class T {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfig.class);

        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        System.out.println(helloWorld.hello);

        HelloWorld helloWorldx = (HelloWorld) ctx.getBean("datasource");
        System.out.println(helloWorldx.hello);

    }
}
