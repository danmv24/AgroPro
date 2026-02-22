package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.StatusCode;
import lombok.Getter;

import java.util.List;

@Getter
public class MachineryStatusConflictException extends StatusConflictException {

    private final List<Long> machineryIds;
    private final List<Integer> inventoryNumbers;
    private final StatusCode expectedStatus;

    public MachineryStatusConflictException(List<Long> machineryIds, List<Integer> inventoryNumbers, StatusCode expectedStatus) {
        super("Техника с инвентарными номерами " + inventoryNumbers + " не может быть переведена в статус '" + expectedStatus.getStatus() + "'");
        this.machineryIds = machineryIds;
        this.expectedStatus = expectedStatus;
        this.inventoryNumbers = inventoryNumbers;
    }
}
