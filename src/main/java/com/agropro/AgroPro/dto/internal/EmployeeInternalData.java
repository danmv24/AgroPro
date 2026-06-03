package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class EmployeeInternalData {

    private final Long id;

    private final String surname;

    private final String name;

    private final String patronymic;

    private final EmployeePosition position;

    private final PaymentType paymentType;

    private final BigDecimal salary;

    private final Gender gender;

    private final LocalDate hireDate;

}
