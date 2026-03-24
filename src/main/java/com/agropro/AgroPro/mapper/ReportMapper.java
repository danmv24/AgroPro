package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.model.Report;

import java.time.LocalDateTime;

public class ReportMapper {

    public static Report toModel(ReportForm reportForm, String filename, LocalDateTime createdAt) {
        return Report.builder()
                .reportType(reportForm.getReportType())
                .startDate(reportForm.getStartDate())
                .endDate(reportForm.getEndDate())
                .createdAt(createdAt)
                .filename(filename)
                .build();
    }

}
