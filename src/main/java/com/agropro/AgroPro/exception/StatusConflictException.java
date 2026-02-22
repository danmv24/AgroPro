package com.agropro.AgroPro.exception;

public abstract class StatusConflictException extends RuntimeException {
    public StatusConflictException(String message) {
        super(message);
    }
}
