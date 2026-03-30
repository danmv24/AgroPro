package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidWorkTypeException extends InvalidEnumValueException {

    private final String workType;

    public InvalidWorkTypeException(String workType) {
        super("Тип работы с названием '" + workType + "' не найден");
        this.workType = workType;
    }
}
