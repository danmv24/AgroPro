package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
public class EquipmentNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public EquipmentNotFoundException(HttpStatus status, Set<Long> missingIds) {
        super("Не найдено оборудование с id: " + missingIds);
        this.status = status;
    }
}
