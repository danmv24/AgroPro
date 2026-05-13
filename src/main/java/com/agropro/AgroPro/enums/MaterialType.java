package com.agropro.AgroPro.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MaterialType {

    FUEL("Топливо"),
    SEEDS("Семена"),
    FERTILIZER("Удобрение");

    private final String materialCode;

    @JsonValue
    public String getMaterialCode() {
        return materialCode;
    }

}
