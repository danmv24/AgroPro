package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class UploadStorageServiceException extends StorageServiceException {

    private final String filename;

    public UploadStorageServiceException(String filename, Throwable cause) {
        super("Ошибка при загрузке файла '" + filename + "'", cause);
        this.filename = filename;
    }
}
