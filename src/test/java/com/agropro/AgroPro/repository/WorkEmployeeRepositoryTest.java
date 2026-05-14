package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.*;
import com.agropro.AgroPro.model.*;
import com.agropro.AgroPro.projection.CropLaborCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class WorkEmployeeRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private WorkEmployeeRepository workEmployeeRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FieldPlantingRepository fieldPlantingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @BeforeEach
    void setUp() {
        Field f1 = fieldRepository.findById(1L).orElseThrow();
        Field f2 = fieldRepository.findById(2L).orElseThrow();

        fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f1.getId())
                .cropType(CropType.WINTER_WHEAT)
                .plantingDate(LocalDate.of(2025, 10, 1))
                .build());
        fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f2.getId())
                .cropType(CropType.SPRING_WHEAT)
                .plantingDate(LocalDate.of(2026, 4, 1))
                .build());

        Employee emp1 = employeeRepository.save(Employee.builder()
                .surname("Иванов")
                .name("Иван")
                .patronymic("Иванович")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(new BigDecimal(500))
                .hireDate(LocalDate.of(2026, 1, 1))
                .gender(Gender.MALE)
                .build());
        Employee emp2 = employeeRepository.save(Employee.builder()
                .surname("Петров")
                .name("Петр")
                .patronymic("Петрович")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(new BigDecimal(600))
                .hireDate(LocalDate.of(2026, 1, 1))
                .gender(Gender.MALE)
                .build());

        Work w1 = workRepository.save(Work.builder()
                .workType(WorkType.SOWING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 10, 9, 0))
                .endDate(LocalDateTime.of(2026, 6, 10, 17, 0))
                .build());
        Work w2 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 11, 9, 0))
                .endDate(LocalDateTime.of(2026, 6, 11, 13, 0))
                .build());
        Work w3 = workRepository.save(Work.builder()
                .workType(WorkType.FERTILIZING)
                .fieldId(f2.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 20, 8, 0))
                .endDate(LocalDateTime.of(2026, 6, 20, 18, 0))
                .build());
        Work w4 = workRepository.save(Work.builder()
                .workType(WorkType.SOWING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 10, 9, 0))
                .endDate(LocalDateTime.of(2026, 5, 10, 17, 0))
                .build());

        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(w1.getId())
                .employeeId(emp1.getId())
                .build());
        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(w2.getId())
                .employeeId(emp1.getId())
                .build());
        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(w3.getId())
                .employeeId(emp2.getId())
                .build());
        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(w4.getId())
                .employeeId(emp1.getId())
                .build());
    }

    @Test
    void findCropLaborCostsByDateRange_shouldCalculateCostCorrectly() {
        List<CropLaborCost> result = workEmployeeRepository.findCropLaborCostsByDateRange(LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30));

        assertThat(result).hasSize(2);

        CropLaborCost winterWheatCost = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT)
                .findFirst()
                .orElseThrow();

        CropLaborCost springWheatCost = result.stream()
                .filter(r -> r.getCropType() == CropType.SPRING_WHEAT)
                .findFirst()
                .orElseThrow();

        assertThat(winterWheatCost.getTotalSalary()).isEqualByComparingTo(new BigDecimal(6000));

        assertThat(springWheatCost.getTotalSalary()).isEqualByComparingTo(new BigDecimal(6000));
    }

    @Test
    void findCropLaborCostsByDateRange_shouldReturnEmptyForFutureDate() {
        List<CropLaborCost> result = workEmployeeRepository.findCropLaborCostsByDateRange(LocalDate.of(2027, 1, 1),
                LocalDate.of(2027, 12, 31));

        assertThat(result).isEmpty();
    }

}
