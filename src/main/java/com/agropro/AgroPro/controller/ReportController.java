package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<Void> createReport(@Valid @RequestBody ReportForm reportForm) {
        reportService.createReport(reportForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
