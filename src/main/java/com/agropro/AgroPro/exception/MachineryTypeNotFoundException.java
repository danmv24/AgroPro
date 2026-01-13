package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MachineryTypeNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public MachineryTypeNotFoundException(HttpStatus httpStatus, Long machineryTypeId) {
        super("Тип машины с id = " + machineryTypeId + " не найден");
        this.httpStatus = httpStatus;
    }
}
