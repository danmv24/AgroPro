package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public EmployeeNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
