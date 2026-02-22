package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.StatusCode;
import lombok.Getter;

import java.util.Set;

@Getter
public class EquipmentStatusConflictException extends StatusConflictException {

    private final Set<Long> equipmentIds;
    private final StatusCode expectedStatus;

    public EquipmentStatusConflictException(Set<Long> equipmentIds, StatusCode expectedStatus) {
        super("Оборудование с id: " + equipmentIds + " не может быть переведена в статус '" + expectedStatus + "'");
        this.equipmentIds = equipmentIds;
        this.expectedStatus = expectedStatus;
    }
}
