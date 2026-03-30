package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidPositionException extends InvalidEnumValueException {

    private final String position;

    public InvalidPositionException(String position) {
        super("Должность с названием '" + position + "' не найдена");
        this.position = position;
    }

}
