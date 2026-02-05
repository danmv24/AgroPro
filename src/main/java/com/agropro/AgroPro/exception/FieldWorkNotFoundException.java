package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class FieldWorkNotFoundException extends RuntimeException {

    private final Long workId;

    public FieldWorkNotFoundException(Long workId) {
        super("Работа с id = " + workId + " не найдена");
        this.workId = workId;
    }
}
