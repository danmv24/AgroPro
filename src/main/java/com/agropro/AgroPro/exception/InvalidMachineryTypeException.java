package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidMachineryTypeException extends InvalidEnumValueException {

    private final String machineryType;

    public InvalidMachineryTypeException(String machineryType) {
        super("Тип машины с названием '" + machineryType + "' не найден");
        this.machineryType = machineryType;
    }
}
