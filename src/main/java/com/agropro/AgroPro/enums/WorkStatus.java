package com.agropro.AgroPro.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WorkStatus {

    PLANNED("Запланировано"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена"),
    CANCELLED("Отменена");

    private final String workStatusName;

    @JsonValue
    public String getWorkStatusName() {
        return workStatusName;
    }

    @JsonCreator
    public static WorkStatus fromString(String value) {
        return Arrays.stream(WorkStatus.values())
                .filter(ws -> ws.getWorkStatusName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow();
    }
}
