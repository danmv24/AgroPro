package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidFertilizerTypeException extends InvalidEnumValueException {

    private final String fertilizerTypeName;

    public InvalidFertilizerTypeException(String fertilizerTypeName) {
        super("Тип удобрения '" + fertilizerTypeName + "' не найден");
        this.fertilizerTypeName = fertilizerTypeName;
    }
}
