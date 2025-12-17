package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FieldNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public FieldNotFoundException(HttpStatus httpStatus, Long fieldId) {
        super("Поле с id " + fieldId + " не найдено");
        this.httpStatus = httpStatus;
    }
}
