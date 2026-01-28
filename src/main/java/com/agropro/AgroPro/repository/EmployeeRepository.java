package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;

import java.util.List;

public interface EmployeeRepository {

    void save(Employee employee);

    List<Employee> findAll();

    List<EmployeeBasicInfoView> findEmployeesWherePaymentTypeIsHourly();

    boolean existsByEmployeeId(Long employeeId);

    List<EmployeeBasicInfoView> findEmployeesWherePositionIsMechanizator();

}
