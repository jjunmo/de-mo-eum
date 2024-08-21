package com.example.springblog.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class MyInternalServerException extends RuntimeException {

    private final String errorData;
    public MyInternalServerException(String message, String errorData) {
        super(message);
        this.errorData = errorData;
    }
}