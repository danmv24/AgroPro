package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class EquipmentTypeNotFoundException extends RuntimeException {

    private final Long machineryTypeId;

    public EquipmentTypeNotFoundException(Long machineryTypeId) {
        super("Тип оборудования с id = " + machineryTypeId + " не найден");
        this.machineryTypeId = machineryTypeId;
    }
}
