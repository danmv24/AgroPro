package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
public class MachineryNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public MachineryNotFoundException(HttpStatus status, Set<Long> missingIds) {
        super("Не найдена техника с id: " + missingIds);
        this.status = status;
    }
}
