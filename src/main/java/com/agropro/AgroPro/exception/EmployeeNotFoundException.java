package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public EmployeeNotFoundException(HttpStatus status, Long employeeId) {
        super("Сотрудник с id " + employeeId + " не найден");
        this.status = status;
    }

    public EmployeeNotFoundException(HttpStatus status, Set<Long> missingIds) {
        super("Не найдены сотрудники со следующими id: " + missingIds);
        this.status = status;
    }

}
