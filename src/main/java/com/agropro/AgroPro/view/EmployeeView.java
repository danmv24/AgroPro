package com.agropro.AgroPro.view;

import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Builder
public class EmployeeView {

    private final String surname;

    private final String name;

    private final String patronymic;

    private final String position;

    private final String paymentType;

    private final BigDecimal salary;

}
