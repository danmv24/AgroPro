package com.agropro.AgroPro.exception;

import org.springframework.http.HttpStatus;

public class PositionNotFoundException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public PositionNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
