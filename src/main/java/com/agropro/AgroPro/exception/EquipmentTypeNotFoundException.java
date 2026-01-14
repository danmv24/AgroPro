package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EquipmentTypeNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public EquipmentTypeNotFoundException(HttpStatus httpStatus, Long machineryTypeId) {
        super("Тип оборудования с id = " + machineryTypeId + " не найден");
        this.httpStatus = httpStatus;
    }
}
