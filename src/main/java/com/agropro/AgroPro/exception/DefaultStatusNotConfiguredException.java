package com.agropro.AgroPro.exception;

public class DefaultStatusNotConfiguredException extends RuntimeException {
    public DefaultStatusNotConfiguredException(String statusCode) {
        super("Статус по умолчанию '" + statusCode + "' не найден");
    }
}
