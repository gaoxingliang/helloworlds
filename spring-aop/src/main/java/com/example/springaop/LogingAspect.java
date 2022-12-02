package com.example.springaop;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
@Aspect
public class LogingAspect {

    @Pointcut("within(com.example.springaop.controller.*)")
    public void ctrlMethod() {

    }

    @Pointcut("execution(public * *(..)) ")
    public void publicMethod() {}

    @Around("ctrlMethod() && publicMethod()")
    public Object loggingAndTiming(ProceedingJoinPoint jp) throws Throwable {
        long t1 = System.currentTimeMillis();
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        String args = Arrays.toString(jp.getArgs());
        try {
            return jp.proceed();
        } catch (Throwable e) {
            throw e;
        }
        finally {
            System.out.println(String.format("%s.%s with args: %s , cost time: %d",
                    className, methodName, args, System.currentTimeMillis() - t1));
        }
    }
}
