package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.component.ReportFactory;
import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.dto.response.ReportDownloadResponse;
import com.agropro.AgroPro.dto.response.ReportResponse;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.exception.ReportNotFoundException;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.mapper.ReportMapper;
import com.agropro.AgroPro.model.Report;
import com.agropro.AgroPro.repository.ReportRepository;
import com.agropro.AgroPro.service.ReportService;
import com.agropro.AgroPro.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
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

    @Override
    public Slice<ReportResponse> getReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Slice<Report> reports = reportRepository.findAll(pageable);

        return reports.map(ReportMapper::toResponse);
    }

    @Override
    public ReportDownloadResponse downloadReport(Long id) {
        try {
            Report report = reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(id));

            InputStream reportInputStream = storageService.downloadFile(report.getFilename());
            Resource resource = new InputStreamResource(reportInputStream);

            return ReportMapper.toDownloadResponse(resource, report.getFilename());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(id));
        storageService.deleteFile(report.getFilename());
        reportRepository.delete(report);
    }

    private String generateFileName(ReportType reportType) {
        String uuid = UUID.randomUUID().toString();

        return reportType.getReportTypeName().toLowerCase() + "_" + LocalDate.now() + "_" + uuid + ".xlsx";
    }


}
