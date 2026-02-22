package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidEquipmentStatusException extends InvalidResourceStatusException {

    private final List<Long> equipmentIds;
    private final List<Integer> inventoryNumbers;

    public InvalidEquipmentStatusException(List<Long> equipmentIds, List<Integer> inventoryNumbers) {
        super("Оборудование с инвентарными номерами " + inventoryNumbers + " имеет недопустимый статус и не может участвовать в работе");
        this.equipmentIds = equipmentIds;
        this.inventoryNumbers = inventoryNumbers;
    }
}
