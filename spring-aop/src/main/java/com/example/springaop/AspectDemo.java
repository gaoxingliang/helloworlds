package com.example.springaop;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class AspectDemo {

    @Pointcut("within(com.example.springaop.controller..*)")
    public void allMethodsUnderPackage(){
    }

    @Before("allMethodsUnderPackage()")
    public void allMethodsUnderPackageAdvice(JoinPoint jp) {

    }
}
