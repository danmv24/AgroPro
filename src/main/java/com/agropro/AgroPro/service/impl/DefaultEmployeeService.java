package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.exception.EmployeeNotAvailableException;
import com.agropro.AgroPro.exception.EmployeeNotFoundException;
import com.agropro.AgroPro.exception.EmptyCollectionException;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.mapper.EmployeeMapper;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import com.agropro.AgroPro.view.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void createEmployee(EmployeeForm employeeForm) {
        employeeRepository.save(EmployeeMapper.toModel(employeeForm));
    }

    @Override
    public List<EmployeeView> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(EmployeeMapper::toView)
                .toList();
    }

//    @Override
//    public List<EmployeeBasicInfoView> getHourlyPaidEmployees() {
//        return employeeRepository.findEmployeesByPaymentType(PaymentType.HOURLY);
//    }

    @Override
    public List<EmployeeBasicInfoView> getMechanizators() {
        List<Employee> employees = employeeRepository.findEmployeesByPosition(EmployeePosition.MACHINE_OPERATOR);

        return employees.stream()
                .map(EmployeeMapper::toBasicInfoView)
                .toList();
    }

    @Override
    public void validateEmployeesExistByIds(Set<Long> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        Set<Long> existingIds = employeeRepository.findEmployeeIdsByIdIn(employeeIds);

        if (existingIds.size() != employeeIds.size()) {
            Set<Long> missingIds = new HashSet<>(employeeIds);
            missingIds.removeAll(existingIds);
            throw new EmployeeNotFoundException(missingIds);
        }

    }

    @Override
    public void validateEmployeesAvailability(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            throw new EmptyCollectionException();
        }
        
        List<WorkStatus> fieldWorkStatuses = List.of(WorkStatus.PLANNED, WorkStatus.IN_PROGRESS);

        List<Long> conflictEmployeeIds = employeeRepository.findConflictEmployeeIdsByDateTime(employeeIds,
                fieldWorkStatuses, Timestamp.valueOf(startDateOfWork), Timestamp.valueOf(endDateOfWork));

        if (!conflictEmployeeIds.isEmpty()) {
            throw new EmployeeNotAvailableException(conflictEmployeeIds);
        }
    }

    @Override
    public List<EmployeeBasicInfoView> getEmployeesByWorkId(Long workId) {
        List<Employee> employees = employeeRepository.findEmployeesByFieldWorkId(workId);

        return employees.stream()
                .map(EmployeeMapper::toBasicInfoView)
                .toList();
    }

}
