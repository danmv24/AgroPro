package com.agropro.AgroPro.view;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeView {

    private String surname;

    private String name;

    private String patronymic;

    private String position;

    private String paymentType;

    private BigDecimal salary;

}
