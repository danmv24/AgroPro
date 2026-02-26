package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.WorkTypeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum WorkType {

    PLOWING("Пахота"),
    CULTIVATION("Культивация"),
    HARROWING("Боронование"),
    SOWING("Посев"),
    FERTILIZING("Внесение удобрений"),
    HARVESTING("Уборка урожая"),
    WATERING("Полив");

    private final String workTypeName;

    @JsonValue
    public String getWorkTypeName() {
        return workTypeName;
    }

    @JsonCreator
    public static WorkType fromString(String value) {
        return Arrays.stream(WorkType.values())
                .filter(wt -> wt.getWorkTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new WorkTypeNotFoundException(value));
    }

}
