package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.EquipmentTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum EquipmentType {

    SEEDER("Сеялка"),
    CULTIVATOR("Культиватор"),
    DISKATOR("Дискатор"),
    TRAILER("Прицеп"),
    MOWER("Косилка"),
    FERTILIZER_DISPENSER("Распределитель удобрений"),
    SPRAYER("Опрыскиватель"),
    PLOW("Плуг");


    private final String equipmentType;

    private static final Map<String, EquipmentType> ENUM_MAP = new HashMap<>();

    static {
        for (EquipmentType equipmentType : EquipmentType.values()) {
            ENUM_MAP.put(equipmentType.getEquipmentType(), equipmentType);
        }
    }

    public static EquipmentType fromString(String equipmentType) {
        EquipmentType equipmentTypeName = ENUM_MAP.get(equipmentType);
        if (equipmentTypeName == null) {
            throw new EquipmentTypeNotFoundException(equipmentType);
        }
        return equipmentTypeName;
    }
    
}
