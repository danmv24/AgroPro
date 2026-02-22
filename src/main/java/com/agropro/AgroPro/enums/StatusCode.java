package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.StatusCodeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum StatusCode {

    IN_OPERATION("В работе"),
    UNDER_REPAIR("На ремонте"),
    IDLE("Простой"),
    DECOMMISSIONED("Списана");

    private final String status;

    private static final Map<String, StatusCode> ENUM_MAP = new HashMap<>();

    static {
        for (StatusCode status : StatusCode.values()) {
            ENUM_MAP.put(status.getStatus(), status);
        }
    }

    public static StatusCode fromString(String status) {
        StatusCode statusCode = ENUM_MAP.get(status);
        if (statusCode == null) {
            throw new StatusCodeNotFoundException(status);
        }
        return statusCode;
    }

}
