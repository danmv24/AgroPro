package com.agropro.AgroPro.model;

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
    private Long employeeId;

    @Column("surname")
    private String surname;

    @Column("name")
    private String name;

    @Column("patronymic")
    private String patronymic;

    @Column("position_id")
    private Long positionId;

    @Column("payment_type")
    private PaymentType paymentType;

    @Column("salary")
    private BigDecimal salary;

    @Column("hire_date")
    private LocalDate hireDate;

}
