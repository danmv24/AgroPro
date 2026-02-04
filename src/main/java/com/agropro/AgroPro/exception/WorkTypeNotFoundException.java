package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WorkTypeNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public WorkTypeNotFoundException(HttpStatus status, Long workTypeId) {
        super("Тип работы с id = " + workTypeId + " не найден");
        this.status = status;
    }
}
