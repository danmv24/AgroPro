package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class DeleteStorageServiceException extends RuntimeException {

    private final String filename;

    public DeleteStorageServiceException(String filename, Throwable cause) {
        super("Ошибка при удалении файла '" + filename + "'", cause);
        this.filename = filename;
    }
}
