package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class EmployeeNotFoundException extends NotFoundException {

    private final Long employeeId;

    private final Set<Long> missingIds;

    public EmployeeNotFoundException(Long employeeId) {
        super("Сотрудник с id " + employeeId + " не найден");
        this.employeeId = employeeId;
        this.missingIds = null;
    }

    public EmployeeNotFoundException(Set<Long> missingIds) {
        super("Не найдены сотрудники со следующими id: " + missingIds);
        this.missingIds = missingIds;
        this.employeeId = null;
    }

}
