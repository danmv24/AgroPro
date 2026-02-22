package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.PositionNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum EmployeePosition {

    GENERAL_DIRECTOR("Генеральный директор"),
    ACCOUNTANT("Бухгалтер"),
    MANAGER("Менеджер"),
    AGRONOMIST("Агроном"),
    TECHNOLOGIST("Технолог"),
    MACHINE_OPERATOR("Механизатор");

    private final String position;

    private static final Map<String, EmployeePosition> ENUM_MAP = new HashMap<>();

    static {
        for (EmployeePosition employeePosition : EmployeePosition.values()) {
            ENUM_MAP.put(employeePosition.getPosition(), employeePosition);
        }
    }

    public static EmployeePosition fromString(String position) {
        EmployeePosition employeePosition = ENUM_MAP.get(position);
        if (employeePosition == null) {
            throw new PositionNotFoundException(position);
        }
        return employeePosition;
    }

}
