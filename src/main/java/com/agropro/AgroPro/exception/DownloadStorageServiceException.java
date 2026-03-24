package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class DownloadStorageServiceException extends StorageServiceException {

    private final String filename;

    public DownloadStorageServiceException(String filename, Throwable cause) {
        super("Ошибка при скачивании файла '" + filename + "'", cause);
        this.filename = filename;
    }
}
