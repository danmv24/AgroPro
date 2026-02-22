package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.WorkTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum WorkType {

    PLOWING("Пахота"),
    CULTIVATION("Культивация"),
    HARROWING("Боронование"),
    SOWING("Посев"),
    FERTILIZING("Внесение удобрений"),
    HARVESTING("Уборка урожая"),
    WATERING("Полив");

    private final String workType;

    private static final Map<String, WorkType> ENUM_MAP = new HashMap<>();

    static {
        for (WorkType workType : WorkType.values()) {
            ENUM_MAP.put(workType.getWorkType(), workType);
        }
    }

    public static WorkType fromString(String workType) {
        WorkType fieldWorkType = ENUM_MAP.get(workType);
        if (fieldWorkType == null) {
            throw new WorkTypeNotFoundException(workType);
        }
        return fieldWorkType;
    }

}
