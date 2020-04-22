package com.example.schedule.ctrl;


import com.example.schedule.exceptionExample.MyException;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 一个很好的说明interceptor和filter区别的文章:
 * http://codethataint.com/blog/filters-vs-handlers/
 * 主要有以下几点:
 *   1. Filter 是servlet上下文的  可以修改 request, response.
 *      Interceptor是spring上下文的   可以获取spring管理的bean 所以可以做更复杂的逻辑
 *   2. Filter 主要方法是doFilter.
 *      Interceptor主要方法是preHandle, postHandle, afterCompletion
 *              prehandle在进入ctrl之前
 *              posthandle在渲染之前
 *              afterCompletion发生在渲染完成之后
 *
 *
 *
 * 演示2个问题:
 *      1. doFilter里面的异常没法被global exception handler捕获到
 *
 *
 *      2. 调用逻辑是:
 *         filter.doFilter
 *             ->  interceptor.preHandler
 *             ->  ctrl.doBiz
 *             -> interceptor.postHandler
 *             -> interceptor.complet
 *         filter.finish
 */

@Component
@WebFilter(urlPatterns = "/api/*", filterName = "testfilter")
@Priority(10) // 数字越小越先执行  查看另外的AnotherExampleFilter
public class ExampleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ExampleFilter init");
    }

    @Override
    public void destroy() {
        System.out.println("ExampleFilter destory");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("do ExampleFilter " + request);
        HttpServletRequest r = (HttpServletRequest) request;
        if (r.getRequestURL().toString().contains("testException")) {
            // 模拟鉴权失败
            throw new MyException("not authenticated");
        }
        chain.doFilter(request, response);
        System.out.println("do ExampleFilter finish");
    }
}
