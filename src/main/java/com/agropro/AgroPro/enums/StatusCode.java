package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.InvalidStatusCodeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
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
                .orElseThrow(() -> new InvalidStatusCodeException(value));
    }

}
