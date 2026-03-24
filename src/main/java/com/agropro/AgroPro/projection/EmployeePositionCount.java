package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.EmployeePosition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class EmployeePositionCount {

    private final EmployeePosition position;

    private final int count;

    private final BigDecimal totalSalary;

}
