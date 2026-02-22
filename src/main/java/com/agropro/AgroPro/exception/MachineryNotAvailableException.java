package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class MachineryNotAvailableException extends ResourceNotAvailableException {

    private final List<Long> machineryIds;
    private final List<Integer> inventoryNumbers;

    public MachineryNotAvailableException(List<Long> machineryIds, List<Integer> inventoryNumbers) {
        super("Техника с инвентарными номерами " +  inventoryNumbers + " недоступны в указанный период (уже заняты на других работах)");
        this.machineryIds = machineryIds;
        this.inventoryNumbers = inventoryNumbers;
    }
}
