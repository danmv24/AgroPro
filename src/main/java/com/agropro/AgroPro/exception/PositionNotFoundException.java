package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class PositionNotFoundException extends NotFoundException {

    private final String position;

    public PositionNotFoundException(String position) {
        super("Должность с названием '" + position + "' не найдена");
        this.position = position;
    }

}
