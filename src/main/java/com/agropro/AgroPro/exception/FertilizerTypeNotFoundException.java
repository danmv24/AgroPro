package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class FertilizerTypeNotFoundException extends NotFoundException {

    private final String fertilizerTypeName;

    public FertilizerTypeNotFoundException(String fertilizerTypeName) {
        super("Тип удобрения '" + fertilizerTypeName + "' не найден");
        this.fertilizerTypeName = fertilizerTypeName;
    }
}
