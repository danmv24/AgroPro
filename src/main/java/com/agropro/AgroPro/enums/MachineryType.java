package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.MachineryTypeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MachineryType {

    TRACTOR("Трактор"),
    COMBINE_HARVESTER("Комбайн"),
    TRUCK("Грузовик"),
    TRANSPORT_VEHICLE("Легковой транспорт"),
    FORKLIFT("Погрузчик");

    private final String machineryTypeName;

    @JsonValue
    public String getMachineryTypeName() {
        return machineryTypeName;
    }

    @JsonCreator
    public static MachineryType fromString(String value) {
        return Arrays.stream(MachineryType.values())
                .filter(mt -> mt.getMachineryTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new MachineryTypeNotFoundException(value));
    }

}
