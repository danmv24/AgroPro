package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class MachineryTypeNotFoundException extends RuntimeException {

    private final Long machineryTypeId;

    public MachineryTypeNotFoundException(Long machineryTypeId) {
        super("Тип машины с id = " + machineryTypeId + " не найден");
        this.machineryTypeId = machineryTypeId;
    }
}
