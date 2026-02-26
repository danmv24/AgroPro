package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.WorkStatus;
import lombok.Getter;

@Getter
public class WorkResultNotAllowedException extends RuntimeException {

    private final Long workId;
    private final WorkStatus currentStatus;

    public WorkResultNotAllowedException(Long workId, WorkStatus currentStatus) {
        super("Нельзя внести результат для работы с id = " + workId + ", так как она имеет статус '" + currentStatus.getWorkStatusName() + "'");
        this.workId = workId;
        this.currentStatus = currentStatus;
    }
}
