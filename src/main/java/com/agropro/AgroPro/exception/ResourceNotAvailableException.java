package com.agropro.AgroPro.exception;

public abstract class ResourceNotAvailableException extends RuntimeException {
    public ResourceNotAvailableException(String message) {
        super(message);
    }
}
