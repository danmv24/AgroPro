package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee save(Employee employee);

    List<Employee> findAll();

}
