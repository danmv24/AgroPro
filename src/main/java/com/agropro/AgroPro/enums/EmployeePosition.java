package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.PositionNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum EmployeePosition {

    GENERAL_DIRECTOR("Генеральный директор"),
    ACCOUNTANT("Бухгалтер"),
    MANAGER("Менеджер"),
    AGRONOMIST("Агроном"),
    TECHNOLOGIST("Технолог"),
    MACHINE_OPERATOR("Механизатор");

    private final String positionName;

    @JsonValue
    public String getPositionName() {
        return positionName;
    }

    @JsonCreator
    public static EmployeePosition fromString(String value) {
        return Arrays.stream(EmployeePosition.values())
                .filter(position -> position.getPositionName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new PositionNotFoundException(value));
    }

}
