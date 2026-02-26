package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.StatusCodeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum StatusCode {

    IN_OPERATION("В работе"),
    UNDER_REPAIR("На ремонте"),
    IDLE("Простой"),
    DECOMMISSIONED("Списана");

    private final String statusName;

    @JsonValue
    public String getStatusName() {
        return statusName;
    }

    @JsonCreator
    public static StatusCode fromString(String value) {
        return Arrays.stream(StatusCode.values())
                .filter(sc -> sc.getStatusName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new StatusCodeNotFoundException(value));
    }

}
