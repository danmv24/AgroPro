package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.EmployeeInternalData;
import com.agropro.AgroPro.dto.request.EmployeeRequest;
import com.agropro.AgroPro.dto.response.EmployeeBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EmployeeResponse;
import com.agropro.AgroPro.model.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static Employee toModel(EmployeeRequest employeeForm) {
        return Employee.builder()
                .surname(employeeForm.getSurname())
                .name(employeeForm.getName())
                .patronymic(employeeForm.getPatronymic())
                .position(employeeForm.getPosition())
                .paymentType(employeeForm.getPaymentType())
                .salary(employeeForm.getSalary())
                .gender(employeeForm.getGender())
                .hireDate(employeeForm.getHireDate())
                .build();
    }

    public static EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .position(employee.getPosition())
                .paymentType(employee.getPaymentType().getPaymentTypeName())
                .salary(employee.getSalary())
                .build();
    }

    public static EmployeeBasicInfoResponse toBasicInfoResponse(Employee employee) {
        return EmployeeBasicInfoResponse.builder()
                .employeeId(employee.getId())
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .build();
    }

    public static EmployeeInternalData toInternalData(Employee employee) {
        return EmployeeInternalData.builder()
                .id(employee.getId())
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .position(employee.getPosition())
                .gender(employee.getGender())
                .paymentType(employee.getPaymentType())
                .hireDate(employee.getHireDate())
                .salary(employee.getSalary())
                .build();
    }
}
