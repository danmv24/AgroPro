package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.InvalidEquipmentTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum EquipmentType {

    SEEDER("Сеялка"),
    CULTIVATOR("Культиватор"),
    DISKATOR("Дискатор"),
    TRAILER("Прицеп"),
    MOWER("Косилка"),
    FERTILIZER_DISPENSER("Распределитель удобрений"),
    SPRAYER("Опрыскиватель"),
    PLOW("Плуг"),
    HARROW("Борона"),
    ROLLING_RINKS("Прикатывающие катки"),
    IRRIGATION_SYSTEM("Поливная установка");


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
                .orElseThrow(() -> new InvalidEquipmentTypeException(value));
    }
    
}
