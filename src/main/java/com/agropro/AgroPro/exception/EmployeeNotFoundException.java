package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public EmployeeNotFoundException(HttpStatus status, Long employeeId) {
        super("Сотрудник с id " + employeeId + " не найден");
        this.status = status;
    }
}
