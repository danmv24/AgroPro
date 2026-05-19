package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Role;

public class RoleMapper {

    private RoleMapper() {
    }

    public static Role fromPosition(EmployeePosition position) {
        return switch (position) {
            case GENERAL_DIRECTOR -> Role.GENERAL_DIRECTOR;
            case ACCOUNTANT -> Role.ACCOUNTANT;
            case MANAGER -> Role.MANAGER;
            case AGRONOMIST -> Role.AGRONOMIST;
            case MACHINE_OPERATOR -> Role.MACHINE_OPERATOR;

            default -> throw new IllegalStateException("Для должности роль не найдена" + position);
        };
    }

}
