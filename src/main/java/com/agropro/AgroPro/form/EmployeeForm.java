package com.agropro.AgroPro.form;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeForm {

    private String surname;

    private String name;

    private String patronymic;

    private String position;

    private String paymentType;

    private BigDecimal salary;

    private LocalDate hireDate;

}
