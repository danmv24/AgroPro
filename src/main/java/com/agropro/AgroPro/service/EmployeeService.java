package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import com.agropro.AgroPro.view.EmployeeView;

import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeForm employeeForm);

    List<EmployeeView> getEmployees();

    List<EmployeeBasicInfoView> getHourlyPaidEmployees();

    List<EmployeeBasicInfoView> getMechanizators();

}
