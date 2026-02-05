package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class FieldNotFoundException extends RuntimeException {

    private final Long fieldId;

    public FieldNotFoundException(Long fieldId) {
        super("Поле с id " + fieldId + " не найдено");
        this.fieldId = fieldId;
    }
}
