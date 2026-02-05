package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class EquipmentNotFoundException extends RuntimeException {

    private final Set<Long> missingIds;

    public EquipmentNotFoundException(Set<Long> missingIds) {
        super("Не найдено оборудование с id: " + missingIds);
        this.missingIds = missingIds;
    }
}
