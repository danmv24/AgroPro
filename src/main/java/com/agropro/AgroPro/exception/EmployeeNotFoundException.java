package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class EmployeeNotFoundException extends NotFoundException {

    private final Set<Long> missingIds;

    public EmployeeNotFoundException(Set<Long> missingIds) {
        super("Не найдены сотрудники со следующими id: " + missingIds);
        this.missingIds = missingIds;
    }

}
