package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.EmployeeRequest;
import com.agropro.AgroPro.dto.response.EmployeeBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EmployeeResponse;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    void createEmployee(EmployeeRequest employeeForm);

    Slice<EmployeeResponse> getEmployees(int page, int size);

    List<EmployeeBasicInfoResponse> getMechanizators();

    void validateEmployeesExistByIds(Set<Long> employeeIds);

    void validateEmployeesAvailability(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EmployeeBasicInfoResponse> getEmployeesByWorkId(Long workId);
}
