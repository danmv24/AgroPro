package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.*;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.model.WorkEmployee;
import com.agropro.AgroPro.projection.WorkTypeHours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class WorkRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private WorkRepository workRepository;

     @Autowired
     private EmployeeRepository employeeRepository;

     @Autowired
     private WorkEmployeeRepository workEmployeeRepository;

    private Work plannedWorkToStart;

    private Work plannedWorkFuture;

    private Work inProgressCompleted;

    private Work completedWork;

    private Work cultivationWork;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = employeeRepository.save(Employee.builder()
                .surname("Иванов")
                .name("Иван")
                .patronymic("Иванович")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(new BigDecimal("500"))
                .gender(Gender.MALE)
                .hireDate(LocalDate.of(2025, 1, 1))
                .build());

        plannedWorkToStart = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(1L)
                .status(WorkStatus.PLANNED)
                .startDate(LocalDateTime.of(2026, 5, 10, 10, 0))
                .endDate(LocalDateTime.of(2026, 5, 10, 18, 0))
                .build());

        plannedWorkFuture = workRepository.save(Work.builder()
                .workType(WorkType.SOWING)
                .fieldId(2L)
                .status(WorkStatus.PLANNED)
                .startDate(LocalDateTime.of(2026, 6, 1, 10, 0))
                .endDate(LocalDateTime.of(2026, 6, 1, 18, 0))
                .build());

        inProgressCompleted = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(3L)
                .status(WorkStatus.IN_PROGRESS)
                .startDate(LocalDateTime.of(2026, 5, 1, 8, 0))
                .endDate(LocalDateTime.of(2026, 5, 1, 12, 0))
                .build());

        completedWork = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(4L)
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 5, 10, 0))
                .endDate(LocalDateTime.of(2026, 5, 5, 15, 0))
                .build());

        cultivationWork = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(5L)
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 6, 9, 0))
                .endDate(LocalDateTime.of(2026, 5, 6, 13, 0))
                .build());

        workEmployeeRepository.saveAll(List.of(
                WorkEmployee.builder()
                        .workId(completedWork.getId())
                        .employeeId(employee.getId())
                        .build(),
                WorkEmployee.builder()
                        .workId(cultivationWork.getId())
                        .employeeId(employee.getId())
                        .build()
        ));
    }

    @Test
    void findWorksToStart_shouldReturnWorksReadyToStart() {
        List<Work> worksToStart = workRepository.findWorksToStart(WorkStatus.PLANNED, LocalDateTime.of(2026, 5, 10, 12, 0));

        assertThat(worksToStart).hasSize(1).extracting(Work::getId).containsExactlyInAnyOrder(plannedWorkToStart.getId());
    }


    @Test
    void findWorksToCompleted_shouldReturnExpiredWorks() {
        List<Work> result = workRepository.findWorksToCompleted(WorkStatus.IN_PROGRESS, LocalDateTime.of(2026, 5, 2, 0, 0));

        assertThat(result).hasSize(1).extracting(Work::getId).containsExactly(inProgressCompleted.getId());
    }

    @Test
    void findWorksByStatus_shouldReturnWorksWithPagination() {
        Slice<Work> result = workRepository.findWorksByStatus(WorkStatus.COMPLETED, PageRequest.of(1, 1));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.hasPrevious()).isTrue();
        assertThat(result.hasNext()).isFalse();
    }


    @Test
    void sumWorkingHoursBetweenStartDateAndEndDate_shouldReturnTotalHours() {
        Double totalHours = workRepository.sumWorkingHoursBetweenStartDateAndEndDate(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 31));

        assertThat(totalHours).isEqualTo(9);
    }

    @Test
    void findWorkTypeWithTotalHours_shouldReturnGroupedStatistics() {
        List<WorkTypeHours> result = workRepository.findWorkTypeWithTotalHours(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 31));

        assertThat(result).hasSize(2);

        WorkTypeHours harvesting = result.stream()
                .filter(r -> r.getWorkType() == WorkType.HARVESTING)
                .findFirst()
                .orElseThrow();

        WorkTypeHours cultivation = result.stream()
                .filter(r -> r.getWorkType() == WorkType.CULTIVATION)
                .findFirst()
                .orElseThrow();

        assertThat(harvesting.getTotalHours()).isEqualTo(5.0);
        assertThat(cultivation.getTotalHours()).isEqualTo(4.0);
    }

}
