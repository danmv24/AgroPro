package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class WorkNotFoundException extends NotFoundException {

    private final Long workId;

    public WorkNotFoundException(Long workId) {
        super("Работа с id = " + workId + " не найдена");
        this.workId = workId;
    }
}
