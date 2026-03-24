package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.enums.ReportType;
import lombok.Getter;

@Getter
public class ReportGeneratorNotFoundException extends NotFoundException {

    private final ReportType reportType;

    public ReportGeneratorNotFoundException(ReportType reportType) {
        super("Генератор отчёта для типа '" + reportType.getReportTypeName() + "' не найден");
        this.reportType = reportType;
    }
}
