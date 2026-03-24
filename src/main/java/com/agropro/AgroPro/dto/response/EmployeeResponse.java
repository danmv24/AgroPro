package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.EmployeePosition;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Builder
public class EmployeeResponse {

    private final String surname;

    private final String name;

    private final String patronymic;

    private final EmployeePosition position;

    private final String paymentType;

    private final BigDecimal salary;

}
