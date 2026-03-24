package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ReportNotFoundException extends NotFoundException {

    private final Long reportId;

    public ReportNotFoundException(Long reportId) {
        super("Отчёт не найден");
        this.reportId = reportId;
    }

}
