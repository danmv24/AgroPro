package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.EmployeeForm;
import com.agropro.AgroPro.dto.response.EmployeeBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EmployeeResponse;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.model.Employee;

public class EmployeeMapper {

    public static Employee toModel(EmployeeForm employeeForm) {
        return Employee.builder()
                .surname(employeeForm.getSurname())
                .name(employeeForm.getName())
                .patronymic(employeeForm.getPatronymic())
                .position(employeeForm.getPosition())
                .paymentType(employeeForm.getPaymentType())
                .salary(employeeForm.getSalary())
                .gender(Gender.fromString(employeeForm.getGender()))
                .hireDate(employeeForm.getHireDate())
                .build();
    }

    public static EmployeeResponse toView(Employee employee) {
        return EmployeeResponse.builder()
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .position(employee.getPosition())
                .paymentType(employee.getPaymentType().getPaymentTypeName())
                .salary(employee.getSalary())
                .build();
    }

    public static EmployeeBasicInfoResponse toBasicInfoView(Employee employee) {
        return EmployeeBasicInfoResponse.builder()
                .employeeId(employee.getId())
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .build();
    }

}
