package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.view.EmployeeBasicInfo;

import java.util.List;

public interface EmployeeRepository {

    void save(Employee employee);

    List<Employee> findAll();

    List<EmployeeBasicInfo> findEmployeesWherePaymentTypeIsHourly();

    boolean existByEmployeeId(Long employeeId);

}
