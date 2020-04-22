package com.example.schedule.ctrl;


import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


/**
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
@Priority(9)
public class AnotherExampleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AnotherExampleFilter init");
    }

    @Override
    public void destroy() {
        System.out.println("AnotherExampleFilter destory");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("do AnotherExampleFilter " + request);
        chain.doFilter(request, response);
        System.out.println("do AnotherExampleFilter finish");
    }
}
