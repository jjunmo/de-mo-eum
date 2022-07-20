package com.example.springblog.handler;

public class MyForbiddenException extends RuntimeException {
    public MyForbiddenException(String message) {super(message);}
}
