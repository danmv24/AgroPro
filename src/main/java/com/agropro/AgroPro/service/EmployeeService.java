package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.EmployeeForm;
import com.agropro.AgroPro.dto.response.EmployeeBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EmployeeResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    void createEmployee(EmployeeForm employeeForm);

    List<EmployeeResponse> getEmployees();

    List<EmployeeBasicInfoResponse> getMechanizators();

    void validateEmployeesExistByIds(Set<Long> employeeIds);

    void validateEmployeesAvailability(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EmployeeBasicInfoResponse> getEmployeesByWorkId(Long workId);
}
