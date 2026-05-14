package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class ReportRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        List<Report> reports = List.of(
                Report.builder()
                        .reportType(ReportType.APK_8)
                        .startDate(LocalDate.of(2025, 1, 1))
                        .endDate(LocalDate.of(2025, 12, 31))
                        .createdAt(LocalDateTime.of(2026, 1, 11, 12, 0))
                        .filename("Отчёт №8")
                        .build(),
                Report.builder()
                        .reportType(ReportType.APK_5)
                        .startDate(LocalDate.of(2026, 1, 1))
                        .endDate(LocalDate.of(2026, 6, 30))
                        .createdAt(LocalDateTime.now())
                        .filename("Отчёт №5")
                        .build()
        );
        reportRepository.saveAll(reports);
    }

    @Test
    void findAll_shouldReturnReportsWithPagination() {
        Slice<Report> reports = reportRepository.findAll(PageRequest.of(0, 2));

        assertThat(reports.getContent()).hasSize(2);
        assertThat(reports.hasNext()).isFalse();
    }

}
