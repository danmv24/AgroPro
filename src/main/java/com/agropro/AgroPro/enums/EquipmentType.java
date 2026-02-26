package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.EquipmentTypeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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


    private final String equipmentTypeName;

    @JsonValue
    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    @JsonCreator
    public static EquipmentType fromString(String value) {
        return Arrays.stream(EquipmentType.values())
                .filter(et -> et.getEquipmentTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new EquipmentTypeNotFoundException(value));
    }
    
}
