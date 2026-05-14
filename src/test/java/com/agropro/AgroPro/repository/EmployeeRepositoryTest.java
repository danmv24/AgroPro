package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.*;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.model.WorkEmployee;
import com.agropro.AgroPro.projection.EmployeePositionStatistic;
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
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJdbcTest
public class EmployeeRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkEmployeeRepository workEmployeeRepository;

    private Employee e1;

    private Employee e2;

    private Employee e3;

    private Employee e4;

    private Work work;

    @BeforeEach
    void setUp() {
        e1 = employeeRepository.save(Employee.builder()
                .surname("Иванов")
                .name("Дмитрий")
                .patronymic("Александрович")
                .position(EmployeePosition.GENERAL_DIRECTOR)
                .paymentType(PaymentType.FIXED)
                .salary(new BigDecimal("95000.00"))
                .gender(Gender.MALE)
                .hireDate(LocalDate.of(2024, 3, 15))
                .build());

        e2 = employeeRepository.save(Employee.builder()
                .surname("Петрова")
                .name("Екатерина")
                .patronymic("Владимировна")
                .position(EmployeePosition.MANAGER)
                .paymentType(PaymentType.FIXED)
                .salary(new BigDecimal("75000.00"))
                .gender(Gender.FEMALE)
                .hireDate(LocalDate.of(2025, 7, 22))
                .build());

        e3 = employeeRepository.save(Employee.builder()
                .surname("Смирнов")
                .name("Алексей")
                .patronymic("Игоревич")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(new BigDecimal("400.00"))
                .gender(Gender.MALE)
                .hireDate(LocalDate.of(2021, 1, 10))
                .build());

        e4 = employeeRepository.save(Employee.builder()
                .surname("Козлов")
                .name("Олег")
                .patronymic("Сергеевич")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(new BigDecimal("350.00"))
                .gender(Gender.MALE)
                .hireDate(LocalDate.of(2026, 4, 5))
                .build());

        work = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(1L)
                .status(WorkStatus.PLANNED)
                .startDate(LocalDateTime.of(2026, 5, 13, 17, 30))
                .endDate(LocalDateTime.of(2026, 5, 13, 21, 30))
                .build());

        workEmployeeRepository.saveAll(List.of(
                WorkEmployee.builder()
                        .workId(work.getId())
                        .employeeId(e3.getId())
                        .build(),
                WorkEmployee.builder()
                        .workId(work.getId())
                        .employeeId(e4.getId())
                        .build()
        ));
    }

    @Test
    void findEmployeesByPosition_shouldReturnOnlyMachineOperators() {
        List<Employee> mechanics = employeeRepository.findEmployeesByPosition(EmployeePosition.MACHINE_OPERATOR);

        assertThat(mechanics).hasSize(2);
        assertThat(mechanics).extracting(Employee::getSurname).containsExactlyInAnyOrder("Смирнов", "Козлов");
    }

    @Test
    void findAll_shouldReturnEmployeesWithPagination() {
        Slice<Employee> employees = employeeRepository.findAll(PageRequest.of(0, 2));

        assertThat(employees.getContent()).hasSize(2);
        assertThat(employees.hasNext()).isTrue();
    }

    @Test
    void findEmployeeIdsByIdIn_shouldReturnExistingIds() {
        Set<Long> ids = employeeRepository.findEmployeeIdsByIdIn(Set.of(e1.getId(), e2.getId()));

        assertThat(ids).hasSize(2).containsExactlyInAnyOrder(e1.getId(), e2.getId());
    }

    @Test
    void countEmployeesByGender_shouldReturnCountEmployeesByGender() {
        long count = employeeRepository.countEmployeesByGender(Gender.FEMALE);

        assertThat(count).isEqualTo(1);
    }

    @Test
    void findEmployeeCountByPosition() {
        List<EmployeePositionStatistic> result = employeeRepository.findEmployeeCountByPosition(LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31));

        Optional<EmployeePositionStatistic> generalDirector = result.stream()
                .filter(r -> r.getPosition() == EmployeePosition.GENERAL_DIRECTOR)
                .findFirst();

        assertThat(generalDirector).isPresent();
        assertThat(generalDirector.get().getCount()).isEqualTo(1);
    }

    @Test
    void findEmployeesByWorkId_shouldReturnEmployees() {
        List<Employee> employees = employeeRepository.findEmployeesByWorkId(work.getId());

        assertThat(employees).hasSize(2);
        assertThat(employees).extracting(Employee::getId).containsExactlyInAnyOrder(e3.getId(), e4.getId());
    }

    @Test
    void findEmployeesByWorkId_shouldReturnEmptyList() {
        List<Employee> employees = employeeRepository.findEmployeesByWorkId(6L);

        assertThat(employees).isEmpty();
    }

    @Test
    void findConflictEmployeesByDateTime_shouldReturnConflictingEmployees() {
        Work work = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(1L)
                .status(WorkStatus.IN_PROGRESS)
                .startDate(LocalDateTime.of(2026, 5, 14, 10, 0))
                .endDate(LocalDateTime.of(2026, 5, 14, 18, 0))
                .build());

        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(work.getId())
                .employeeId(e1.getId())
                .build());

        List<Employee> result = employeeRepository.findConflictEmployeesByDateTime(Set.of(e1.getId()), List.of(WorkStatus.IN_PROGRESS),
                        LocalDateTime.of(2026, 5, 14, 12, 0), LocalDateTime.of(2026, 5, 14, 20, 0));

        assertThat(result).hasSize(1).extracting(Employee::getId).containsExactly(e1.getId());
    }

    @Test
    void findConflictEmployeesByDateTime_shouldReturnEmptyWhenNoOverlap() {
        Work work = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(1L)
                .status(WorkStatus.IN_PROGRESS)
                .startDate(LocalDateTime.of(2026, 5, 14, 10, 0))
                .endDate(LocalDateTime.of(2026, 5, 14, 18, 0))
                .build());

        workEmployeeRepository.save(WorkEmployee.builder()
                .workId(work.getId())
                .employeeId(e1.getId())
                .build());

        List<Employee> result = employeeRepository.findConflictEmployeesByDateTime(Set.of(e1.getId()), List.of(WorkStatus.IN_PROGRESS),
                        LocalDateTime.of(2026, 5, 14, 18, 0), LocalDateTime.of(2026, 5, 14, 20, 0));

        assertThat(result).isEmpty();
    }

}
