package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.component.ReportFactory;
import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.mapper.ReportMapper;
import com.agropro.AgroPro.repository.ReportRepository;
import com.agropro.AgroPro.service.ReportService;
import com.agropro.AgroPro.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DefaultReportService implements ReportService {

    private final ReportFactory reportFactory;

    private final StorageService storageService;

    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void createReport(ReportForm reportForm) {
        ReportGenerator generator = reportFactory.getGenerator(reportForm.getReportType());

        byte[] file = generator.generate(reportForm);
        String filename = generateFileName(reportForm.getReportType());

        storageService.uploadFile(file, filename, reportForm.getReportType());

        LocalDateTime createdAt = LocalDateTime.now();
        reportRepository.save(ReportMapper.toModel(reportForm, filename, createdAt));

    }

    private String generateFileName(ReportType reportType) {
        String uuid = UUID.randomUUID().toString();

        return reportType.getReportTypeName().toLowerCase() + "_" + LocalDate.now() + "_" + uuid + ".xlsx";
    }


}
