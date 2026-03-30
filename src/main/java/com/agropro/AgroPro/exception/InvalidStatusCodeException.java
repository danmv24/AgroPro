package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidStatusCodeException extends NotFoundException {

    private final String statusCode;

    public InvalidStatusCodeException(String statusCode) {
        super("Статус '" + statusCode + "' не найден");
        this.statusCode = statusCode;
    }
}
