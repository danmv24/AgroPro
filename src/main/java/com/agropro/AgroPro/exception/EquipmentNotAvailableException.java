package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class EquipmentNotAvailableException extends ResourceNotAvailableException {

    private final List<Long> equipmentIds;
    private final List<Integer> inventoryNumbers;

    public EquipmentNotAvailableException(List<Long> equipmentIds, List<Integer> inventoryNumbers) {
        super("Оборудование с id " + inventoryNumbers + " недоступны в указанный период (уже заняты на других работах)");
        this.equipmentIds = equipmentIds;
        this.inventoryNumbers = inventoryNumbers;
    }
}
