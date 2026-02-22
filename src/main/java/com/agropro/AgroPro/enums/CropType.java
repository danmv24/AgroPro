package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.CropNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    private final String cropType;

    private static final Map<String, CropType> ENUM_MAP = new HashMap<>();

    static {
        for (CropType crop : CropType.values()) {
            ENUM_MAP.put(crop.getCropType(), crop);
        }
    }

    public static CropType fromString(String cropType) {
        CropType crop = ENUM_MAP.get(cropType);
        if (crop == null) {
            throw new CropNotFoundException(cropType);
        }
        return crop;
    }

}
