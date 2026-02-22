package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidMachineryStatusException extends InvalidResourceStatusException {

    private final List<Integer> inventoryNumbers;
    private final List<Long> machineryIds;

    public InvalidMachineryStatusException(List<Long> machineryIds, List<Integer> inventoryNumbers) {
        super("Техника с инвентарными номерами " + inventoryNumbers + " имеет недопустимый статус и не может участвовать в работе");
        this.machineryIds = machineryIds;
        this.inventoryNumbers = inventoryNumbers;
    }
}
