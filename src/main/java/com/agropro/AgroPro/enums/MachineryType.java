package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.MachineryTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum MachineryType {

    TRACTOR("Трактор"),
    COMBINE_HARVESTER("Комбайн"),
    TRUCK("Грузовик"),
    TRANSPORT_VEHICLE("Легковой транспорт"),
    FORKLIFT("Погрузчик");

    private final String machineryType;

    private static final Map<String, MachineryType> ENUM_MAP = new HashMap<>();

    static {
        for (MachineryType machineryType : MachineryType.values()) {
            ENUM_MAP.put(machineryType.getMachineryType(), machineryType);
        }
    }

    public static MachineryType fromString(String machineryType) {
        MachineryType machineryTypeName = ENUM_MAP.get(machineryType);
        if (machineryTypeName == null) {
            throw new MachineryTypeNotFoundException(machineryType);
        }
        return machineryTypeName;
    }

}
