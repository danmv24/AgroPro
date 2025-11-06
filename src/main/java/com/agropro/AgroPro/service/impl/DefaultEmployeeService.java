package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.PositionNotFoundException;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.mapper.EmployeeMapper;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    @Override
    public void addEmployee(EmployeeForm employeeForm) {
        Long positionId = positionRepository.findByPositionName(employeeForm.getPosition())
                .map(Position::getPositionId)
                .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Position not found: " + employeeForm.getPosition()));

        employeeRepository.save(EmployeeMapper.toModel(employeeForm, positionId));
    }

    @Override
    public List<EmployeeView> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> {
                    Position position = positionRepository.findByPositionId(employee.getPositionId())
                            .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Position not found: " + employee.getPositionId()));
                    return EmployeeMapper.toView(employee, position.getPositionName());
                })
                .toList();
    }
}
