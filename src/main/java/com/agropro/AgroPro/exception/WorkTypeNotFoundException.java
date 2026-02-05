package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class WorkTypeNotFoundException extends RuntimeException {

    private final Long workTypeId;

    public WorkTypeNotFoundException(Long workTypeId) {
        super("Тип работы с id = " + workTypeId + " не найден");
        this.workTypeId = workTypeId;
    }
}
