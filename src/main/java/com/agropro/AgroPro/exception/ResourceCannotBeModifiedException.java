package com.agropro.AgroPro.exception;

public abstract class ResourceCannotBeModifiedException extends RuntimeException {
    public ResourceCannotBeModifiedException(String message) {
        super(message);
    }
}
