package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class EquipmentCannotBeModifiedException extends ResourceCannotBeModifiedException {

    private final Long equipmentId;

    public EquipmentCannotBeModifiedException(Long equipmentId) {
        super("Оборудование с id '" + equipmentId + "' не может быть редактирована");
        this.equipmentId = equipmentId;
    }
}
