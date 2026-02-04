package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.EmployeeNotFoundException;
import com.agropro.AgroPro.exception.PositionNotFoundException;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.mapper.EmployeeMapper;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.service.PositionService;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import com.agropro.AgroPro.view.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionService positionService;

    @Override
    @Transactional
    public void addEmployee(EmployeeForm employeeForm) {
        Long positionId = positionService.getPositionIdByPositionName(employeeForm.getPosition())
                .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Должность не найдена: " + employeeForm.getPosition()));

        employeeRepository.save(EmployeeMapper.toModel(employeeForm, positionId));
    }

    @Override
    public List<EmployeeView> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> {
                    String positionName = positionService.getPositionNameByPositionId(employee.getPositionId())
                            .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Не найдена должность с id:" + employee.getPositionId()));
                    return EmployeeMapper.toView(employee, positionName);
                })
                .toList();
    }

    @Override
    public List<EmployeeBasicInfoView> getHourlyPaidEmployees() {
        return employeeRepository.findEmployeesWherePaymentTypeIsHourly();
    }

    @Override
    public List<EmployeeBasicInfoView> getMechanizators() {
        return employeeRepository.findEmployeesWherePositionIsMechanizator();
    }

    @Override
    public void validateEmployeesExistByIds(Set<Long> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            throw new IllegalArgumentException("Отправлен пустой список работников для проверки на существование");
        }

        Set<Long> existingIds = employeeRepository.findExistingEmployeesByIds(employeeIds);

        if (existingIds.size() != employeeIds.size()) {
            Set<Long> missingIds = new HashSet<>(employeeIds);
            missingIds.removeAll(existingIds);
            throw new EmployeeNotFoundException(HttpStatus.BAD_REQUEST, missingIds);
        }

    }

    @Override
    public void validateEmployeesAvailability(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            return;
        }

        List<Long> conflictEmployeeIds = employeeRepository.findConflictEmployeeIdsByDateTime(employeeIds, startDateOfWork, endDateOfWork);

        if (!conflictEmployeeIds.isEmpty()) {
            throw new RuntimeException("Пока что заглушка");
        }
    }

}
