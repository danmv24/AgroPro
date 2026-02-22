package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.enums.PaymentType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    private Long id;

    @Column("surname")
    private String surname;

    @Column("name")
    private String name;

    @Column("patronymic")
    private String patronymic;

    @Column("position")
    private EmployeePosition position;

    @Column("payment_type")
    private PaymentType paymentType;

    @Column("salary")
    private BigDecimal salary;

    @Column("gender")
    private Gender gender;

    @Column("hire_date")
    private LocalDate hireDate;

}
