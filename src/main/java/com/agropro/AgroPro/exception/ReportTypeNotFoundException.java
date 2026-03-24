package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ReportTypeNotFoundException extends NotFoundException {

    private final String reportTypeName;

    public ReportTypeNotFoundException(String reportTypeName) {
        super("Тип отчёта '" + reportTypeName + "' не найден");
        this.reportTypeName = reportTypeName;
    }
}
