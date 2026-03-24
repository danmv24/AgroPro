package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.dto.response.ReportDownloadResponse;
import com.agropro.AgroPro.dto.response.ReportResponse;
import org.springframework.data.domain.Slice;

public interface ReportService {

    void createReport(ReportForm reportForm);

    Slice<ReportResponse> getReports(int page, int size);

    ReportDownloadResponse downloadReport(Long id);

    void deleteReport(Long id);
}
