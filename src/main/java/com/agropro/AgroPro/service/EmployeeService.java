package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.view.EmployeeBasicInfo;
import com.agropro.AgroPro.view.EmployeeView;
import com.agropro.AgroPro.view.WorkRecordView;

import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeForm employeeForm);

    List<EmployeeView> getEmployees();

    List<EmployeeBasicInfo> getHourlyEmployees();

    void addWorkRecord(WorkRecordForm workRecordForm);

    List<WorkRecordView> getAllWorkRecords();

}
