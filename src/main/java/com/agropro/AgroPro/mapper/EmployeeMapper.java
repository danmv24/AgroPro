package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.enums.PaymentType;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import com.agropro.AgroPro.view.EmployeeView;

public class EmployeeMapper {

    public static Employee toModel(EmployeeForm employeeForm) {
        return Employee.builder()
                .surname(employeeForm.getSurname())
                .name(employeeForm.getName())
                .patronymic(employeeForm.getPatronymic())
                .position(EmployeePosition.fromString(employeeForm.getPosition()))
                .paymentType(PaymentType.fromString(employeeForm.getPaymentType()))
                .salary(employeeForm.getSalary())
                .gender(Gender.fromString(employeeForm.getGender()))
                .hireDate(employeeForm.getHireDate())
                .build();
    }

    public static EmployeeView toView(Employee employee) {
        return EmployeeView.builder()
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .position(employee.getPosition().getPosition())
                .paymentType(employee.getPaymentType().getPaymentType())
                .salary(employee.getSalary())
                .build();
    }

    public static EmployeeBasicInfoView toBasicInfoView(Employee employee) {
        return EmployeeBasicInfoView.builder()
                .employeeId(employee.getId())
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .build();
    }

}
