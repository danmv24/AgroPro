package com.agropro.AgroPro.exception;

public abstract class StorageServiceException extends RuntimeException {
    public StorageServiceException(String message, Throwable cause) {
        super(message);
    }
}
