package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.EmployeeNotFoundException;
import com.agropro.AgroPro.exception.PositionNotFoundException;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.mapper.EmployeeMapper;
import com.agropro.AgroPro.mapper.WorkRecordMapper;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.repository.WorkRecordRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeBasicInfo;
import com.agropro.AgroPro.view.EmployeeView;
import com.agropro.AgroPro.view.WorkRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final WorkRecordRepository workRecordRepository;

    @Override
    public void addEmployee(EmployeeForm employeeForm) {
        Long positionId = positionRepository.findByPositionName(employeeForm.getPosition())
                .map(Position::getPositionId)
                .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Должность не найдена: " + employeeForm.getPosition()));

        employeeRepository.save(EmployeeMapper.toModel(employeeForm, positionId));
    }

    @Override
    public List<EmployeeView> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> {
                    Position position = positionRepository.findByPositionId(employee.getPositionId())
                            .orElseThrow(() -> new PositionNotFoundException(HttpStatus.NOT_FOUND, "Не найдена должность с id:" + employee.getPositionId()));
                    return EmployeeMapper.toView(employee, position.getPositionName());
                })
                .toList();
    }

    @Override
    public List<EmployeeBasicInfo> getHourlyEmployees() {
        return employeeRepository.findEmployeesWherePaymentTypeIsHourly();
    }

    @Override
    public void addWorkRecord(WorkRecordForm workRecordForm) {
        if (!employeeRepository.existByEmployeeId(workRecordForm.getEmployeeId())) {
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND, "Не найден сотрудник с id: " + workRecordForm.getEmployeeId());
        }

        workRecordRepository.save(WorkRecordMapper.toModel(workRecordForm));
    }

    @Override
    public List<WorkRecordView> getAllWorkRecords() {
        return workRecordRepository.findAllWorkRecords();
    }


}
