package com.agropro.AgroPro.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum WorkStatus {

    PLANNED("Запланировано"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершено"),
    CANCELLED("Отменена");

    private final String workStatus;

    private static final Map<String, WorkStatus> ENUM_MAP = new HashMap<>();

    static {
        for (WorkStatus workStatus : WorkStatus.values()) {
            ENUM_MAP.put(workStatus.getWorkStatus(), workStatus);
        }
    }

    public static WorkStatus fromString(String workStatus) {
        return ENUM_MAP.get(workStatus);
    }
}
