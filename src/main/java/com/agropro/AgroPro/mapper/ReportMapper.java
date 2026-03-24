package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.dto.response.ReportDownloadResponse;
import com.agropro.AgroPro.dto.response.ReportResponse;
import com.agropro.AgroPro.model.Report;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;

public class ReportMapper {

    public static Report toModel(ReportRequest reportRequest, String filename, LocalDateTime createdAt) {
        return Report.builder()
                .reportType(reportRequest.getReportType())
                .startDate(reportRequest.getStartDate())
                .endDate(reportRequest.getEndDate())
                .createdAt(createdAt)
                .filename(filename)
                .build();
    }

    public static ReportResponse toResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .reportType(report.getReportType().getReportTypeName())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .createdAt(report.getCreatedAt())
                .build();
    }

    public static ReportDownloadResponse toDownloadResponse(Resource resource, String filename) {
        return ReportDownloadResponse.builder()
                .resource(resource)
                .filename(filename)
                .build();
    }
}
