package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PositionNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public PositionNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
