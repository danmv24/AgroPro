package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class MachineryTypeNotFoundException extends NotFoundException {

    private final String machineryType;

    public MachineryTypeNotFoundException(String machineryType) {
        super("Тип машины с названием '" + machineryType + "' не найден");
        this.machineryType = machineryType;
    }
}
