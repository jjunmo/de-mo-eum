package com.example.springblog.handler;

import lombok.Getter;

@Getter
public class MyErrorBody {

    private final String message;
    private final String data;

    public MyErrorBody(String message) {
        this.message = message;
        this.data = "";
    }

    public MyErrorBody(String message, String data) {
        this.message = message;
        this.data = data;
    }
}
