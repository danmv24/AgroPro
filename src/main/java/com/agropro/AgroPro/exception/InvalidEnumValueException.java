package com.agropro.AgroPro.exception;

public abstract class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
