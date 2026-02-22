package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeNotAvailableException extends ResourceNotAvailableException {

    private final List<Long> employeeIds;

    public EmployeeNotAvailableException(List<Long> employeeIds) {
        super("Сотрудники с id " + employeeIds + " недоступны в указанный период (уже заняты на других работах)");
        this.employeeIds = employeeIds;
    }
}
