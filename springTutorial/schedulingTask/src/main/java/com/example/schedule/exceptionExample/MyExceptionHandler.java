package com.example.schedule.exceptionExample;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value=MyException.class)
    public ErrorResp handleMyEx(MyException e, Locale clientLocale, HttpServletRequest request, HttpServletResponse resp) {
        e.printStackTrace();
        System.out.println("Got on exception");
        System.out.println(request.getHeaderNames());
        System.out.println("Resp obj:" + resp);
        System.out.println("Client locale:" + clientLocale);
        return new ErrorResp(e.getMessage());
    }
}
