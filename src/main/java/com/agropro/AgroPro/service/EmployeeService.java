package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import com.agropro.AgroPro.view.EmployeeView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    void createEmployee(EmployeeForm employeeForm);

    List<EmployeeView> getEmployees();
//
//    List<EmployeeBasicInfoView> getHourlyPaidEmployees();

    List<EmployeeBasicInfoView> getMechanizators();

    void validateEmployeesExistByIds(Set<Long> employeeIds);

    void validateEmployeesAvailability(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EmployeeBasicInfoView> getEmployeesByFieldWorkId(Long workId);
}
