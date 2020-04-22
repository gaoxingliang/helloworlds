package com.example.schedule.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *

 Before sending the request to the controller

 Before sending the response to the client

 *

 preHandle() method − This is used to perform operations before sending the request to the controller. This method should return true to return the response to the client.

 postHandle() method − This is used to perform operations before sending the response to the client.

 afterCompletion() method − This is used to perform operations after completing the request and response.

 这个类用来记录比如controller里面的运行时间

 */
@Component
public class MyInterceptor implements HandlerInterceptor {


    public MyInterceptor() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("========== Running Metrics:");
                method2Stat.entrySet().forEach(en -> {
                    System.out.println("\t\t" + en.getKey() + " " + en.getValue());
                });
            }
        }, 10, 30, TimeUnit.SECONDS);
    }

    private ConcurrentHashMap<String, Stat> method2Stat = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("do MyInterceptor prehandle " + request);
        request.setAttribute("$StartNanoTime", System.nanoTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("do MyInterceptor posthandler" + request);
        long elapse = System.nanoTime() - (long)request.getAttribute("$StartNanoTime");
        HandlerMethod m = (HandlerMethod) handler;
        String callName = m.getShortLogMessage();
        Stat s = method2Stat.computeIfAbsent(callName, k -> new Stat());
        s.callCount.incrementAndGet();
        s.totalTimeInNanoSeconds.addAndGet(elapse);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("do MyInterceptor aftercompletion " + request);
        request.removeAttribute("$StartNanoTime");
    }


    static class Stat {
        AtomicInteger callCount = new AtomicInteger(); // 调用次数
        AtomicLong totalTimeInNanoSeconds = new AtomicLong(); // 调用总的时长

        @Override
        public String toString() {
            return "Stat{" +
                    "callCount=" + callCount +
                    ", totalTimeInNanoSeconds=" + totalTimeInNanoSeconds +
                    '}';
        }
    }
}
