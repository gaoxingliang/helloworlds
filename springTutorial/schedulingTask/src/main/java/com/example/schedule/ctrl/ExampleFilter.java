package com.example.schedule.ctrl;


import com.example.schedule.exceptionExample.MyException;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/*", filterName = "testfilter")
public class ExampleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) request;
        if (r.getRequestURL().toString().contains("testException")) {
            // 模拟鉴权失败
            throw new MyException("not authenticated");
        }
        chain.doFilter(request, response);
    }
}
