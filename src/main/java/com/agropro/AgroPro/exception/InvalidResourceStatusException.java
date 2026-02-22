package com.agropro.AgroPro.exception;

public abstract class InvalidResourceStatusException extends RuntimeException {
    public InvalidResourceStatusException(String message) {
        super(message);
    }
}
