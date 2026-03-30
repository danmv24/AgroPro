package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidEquipmentTypeException extends InvalidEnumValueException {

    private final String equipmentType;

    public InvalidEquipmentTypeException(String equipmentType) {
        super("Тип оборудования с названием '" + equipmentType + "' не найден");
        this.equipmentType = equipmentType;
    }
}
