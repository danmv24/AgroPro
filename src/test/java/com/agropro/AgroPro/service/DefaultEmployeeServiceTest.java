package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.EmployeeInternalData;
import com.agropro.AgroPro.dto.request.EmployeeRequest;
import com.agropro.AgroPro.dto.response.EmployeeBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EmployeeResponse;
import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.PaymentType;
import com.agropro.AgroPro.exception.EmployeeNotAvailableException;
import com.agropro.AgroPro.exception.EmployeeNotFoundException;
import com.agropro.AgroPro.exception.EmptyCollectionException;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.service.impl.DefaultEmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultEmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DefaultEmployeeService employeeService;

    @Test
    void createEmployee_ShouldSaveEmployee() {
        EmployeeRequest request = mock(EmployeeRequest.class);
        employeeService.createEmployee(request);

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployees_ShouldReturnSlice() {
        Employee employee = createEmployee(1L);
        Slice<Employee> employeeSlice = new SliceImpl<>(List.of(employee), PageRequest.of(0, 10), false);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(employeeSlice);

        Slice<EmployeeResponse> result = employeeService.getEmployees(0, 10);

        assertEquals(1, result.getContent().size());
        verify(employeeRepository).findAll(any(Pageable.class));
    }

    @Test
    void getMechanizators_ShouldReturnMechanizators() {
        Employee employee = createEmployee(1L);

        when(employeeRepository.findEmployeesByPosition(EmployeePosition.MACHINE_OPERATOR)).thenReturn(List.of(employee));

        List<EmployeeBasicInfoResponse> result = employeeService.getMechanizators();

        assertEquals(1, result.size());
        verify(employeeRepository).findEmployeesByPosition(EmployeePosition.MACHINE_OPERATOR);
    }

    @Test
    void validateEmployeesExistByIds_ShouldThrowWhenIdsNull() {
        assertThrows(EmptyCollectionException.class, () -> employeeService.validateEmployeesExistByIds(null));
    }

    @Test
    void validateEmployeesExistByIds_ShouldThrowWhenIdsEmpty() {
        assertThrows(EmptyCollectionException.class,
                () -> employeeService.validateEmployeesExistByIds(Set.of()));
    }

    @Test
    void validateEmployeesExistByIds_ShouldPassWhenAllExist() {
        Set<Long> ids = Set.of(1L, 2L);

        when(employeeRepository.findEmployeeIdsByIdIn(ids)).thenReturn(ids);
        assertDoesNotThrow(() -> employeeService.validateEmployeesExistByIds(ids));
    }

    @Test
    void validateEmployeesExistByIds_ShouldThrowWhenSomeEmployeesMissing() {
        Set<Long> ids = Set.of(1L, 2L, 3L);

        when(employeeRepository.findEmployeeIdsByIdIn(ids)).thenReturn(Set.of(1L, 2L));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.validateEmployeesExistByIds(ids));
    }

    @Test
    void validateEmployeesAvailability_ShouldThrowWhenIdsNull() {
        assertThrows(EmptyCollectionException.class,
                () -> employeeService.validateEmployeesAvailability(null, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    }

    @Test
    void validateEmployeesAvailability_ShouldThrowWhenIdsEmpty() {
        assertThrows(EmptyCollectionException.class,
                () -> employeeService.validateEmployeesAvailability(Set.of(), LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    }

    @Test
    void validateEmployeesAvailability_ShouldPassWhenNoConflicts() {
        Set<Long> ids = Set.of(1L, 2L);

        when(employeeRepository.findConflictEmployeesByDateTime(
                eq(ids),
                anyList(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of());

        assertDoesNotThrow(() -> employeeService.validateEmployeesAvailability(ids, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    }

    @Test
    void validateEmployeesAvailability_ShouldThrowWhenConflictExists() {
        Set<Long> ids = Set.of(1L, 2L);

        Employee conflictEmployee = createEmployee(1L);

        when(employeeRepository.findConflictEmployeesByDateTime(
                eq(ids),
                anyList(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(conflictEmployee));

        assertThrows(EmployeeNotAvailableException.class,
                () -> employeeService.validateEmployeesAvailability(ids, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    }

    @Test
    void getEmployeesByWorkId_ShouldReturnEmployees() {
        Employee employee = createEmployee(1L);

        when(employeeRepository.findEmployeesByWorkId(100L)).thenReturn(List.of(employee));

        List<EmployeeBasicInfoResponse> result = employeeService.getEmployeesByWorkId(100L);

        assertEquals(1, result.size());
        verify(employeeRepository).findEmployeesByWorkId(100L);
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        Employee employee = createEmployee(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeInternalData result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getEmployeeById_ShouldThrowWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void getEmployeesWithoutAccount_ShouldReturnEmployees() {
        Employee employee = createEmployee(1L);

        when(employeeRepository.findEmployeesWithoutAccount()).thenReturn(List.of(employee));

        List<EmployeeBasicInfoResponse> result = employeeService.getEmployeesWithoutAccount();

        assertEquals(1, result.size());
        verify(employeeRepository).findEmployeesWithoutAccount();
    }

    private Employee createEmployee(Long id) {
        return Employee.builder()
                .id(id)
                .surname("Иванов")
                .name("Иван")
                .patronymic("Иванович")
                .position(EmployeePosition.MACHINE_OPERATOR)
                .paymentType(PaymentType.HOURLY)
                .salary(BigDecimal.valueOf(1000))
                .build();
    }
}
