package com.agropro.AgroPro.mapper;


import com.agropro.AgroPro.enums.PaymentType;
import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.view.EmployeeView;

public class EmployeeMapper {

    public static Employee toModel(EmployeeForm employeeForm, Long positionId) {
        return Employee.builder()
                .surname(employeeForm.getSurname())
                .name(employeeForm.getName())
                .patronymic(employeeForm.getPatronymic())
                .positionId(positionId)
                .paymentType(PaymentType.valueOf(employeeForm.getPaymentType().toUpperCase()))
                .salary(employeeForm.getSalary())
                .hireDate(employeeForm.getHireDate())
                .build();
    }

    public static EmployeeView toView(Employee employee, String positionName) {
        return EmployeeView.builder()
                .surname(employee.getSurname())
                .name(employee.getName())
                .patronymic(employee.getPatronymic())
                .position(positionName)
                .paymentType(employee.getPaymentType().getPaymentTypeName())
                .salary(employee.getSalary())
                .build();
    }

}
