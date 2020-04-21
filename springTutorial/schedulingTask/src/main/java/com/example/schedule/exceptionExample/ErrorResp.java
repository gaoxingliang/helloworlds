package com.example.schedule.exceptionExample;

public class ErrorResp {
    public String msg;
    public long time = System.currentTimeMillis();

    public ErrorResp(String msg) {
        this.msg = msg;
    }
}
