package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class EquipmentTypeNotFoundException extends NotFoundException {

    private final String equipmentType;

    public EquipmentTypeNotFoundException(String equipmentType) {
        super("Тип оборудования с названием '" + equipmentType + "' не найден");
        this.equipmentType = equipmentType;
    }
}
