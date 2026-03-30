package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidReportTypeException extends InvalidEnumValueException {

    private final String reportTypeName;

    public InvalidReportTypeException(String reportTypeName) {
        super("Тип отчёта '" + reportTypeName + "' не найден");
        this.reportTypeName = reportTypeName;
    }
}
