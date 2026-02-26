package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.CropNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CropType {

    WINTER_WHEAT("Пшеница озимая"),
    SPRING_WHEAT("Пшеница яровая"),
    SPRING_BARLEY("Ячмень яровой"),
    SUNFLOWER("Подсолнечник"),
    SOY("Соя"),
    APPLES("Яблоки"),
    PURE_STEAM("Чистый пар");

    private final String cropTypeName;

    @JsonValue
    public String getCropTypeName() {
        return cropTypeName;
    }

    @JsonCreator
    public static CropType fromString(String value) {
        return Arrays.stream(CropType.values())
                .filter(ct -> ct.getCropTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new CropNotFoundException(value));
    }

}
