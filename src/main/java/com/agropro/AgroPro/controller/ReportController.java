package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.dto.response.ReportDownloadResponse;
import com.agropro.AgroPro.dto.response.ReportResponse;
import com.agropro.AgroPro.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<Void> createReport(@Valid @RequestBody ReportRequest reportRequest) {
        reportService.createReport(reportRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Slice<ReportResponse> getAllReports(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size) {
        return reportService.getReports(page, size);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadReport(@PathVariable(name = "id") Long id) {
        ReportDownloadResponse response = reportService.downloadReport(id);
        ContentDisposition contentDisposition = ContentDisposition.attachment().filename(response.getFilename(), StandardCharsets.UTF_8).build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(response.getResource());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable(name = "id") Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

}
