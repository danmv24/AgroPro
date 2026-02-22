package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class WorkTypeNotFoundException extends NotFoundException {

    private final String workType;

    public WorkTypeNotFoundException(String workType) {
        super("Тип работы с названием '" + workType + "' не найден");
        this.workType = workType;
    }
}
