package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class StatusCodeNotFoundException extends NotFoundException {

    private final String statusCode;

    public StatusCodeNotFoundException(String statusCode) {
        super("Статус с названием '" + statusCode + "' не найден");
        this.statusCode = statusCode;
    }
}
