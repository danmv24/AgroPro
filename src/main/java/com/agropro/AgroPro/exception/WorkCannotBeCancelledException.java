package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.WorkStatus;
import lombok.Getter;

@Getter
public class WorkCannotBeCancelledException extends RuntimeException {

    private final Long workId;

    private final WorkStatus fieldWorkStatus;

    public WorkCannotBeCancelledException(Long workId, WorkStatus fieldWorkStatus) {
        super("Работа с id = " + workId + " не может быть отменена. Текущий статус: " + fieldWorkStatus);
        this.workId = workId;
        this.fieldWorkStatus = fieldWorkStatus;
    }
}
