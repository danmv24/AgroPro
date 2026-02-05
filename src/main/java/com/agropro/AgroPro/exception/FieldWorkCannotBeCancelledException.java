package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import lombok.Getter;

@Getter
public class FieldWorkCannotBeCancelledException extends RuntimeException {

    private final Long workId;

    private final FieldWorkStatus fieldWorkStatus;

    public FieldWorkCannotBeCancelledException(Long workId, FieldWorkStatus fieldWorkStatus) {
        super("Работа с id = " + workId + " не может быть отменена. Текущий статус: " + fieldWorkStatus);
        this.workId = workId;
        this.fieldWorkStatus = fieldWorkStatus;
    }
}
