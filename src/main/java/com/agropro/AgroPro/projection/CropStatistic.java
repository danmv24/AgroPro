package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.CropType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class CropStatistic {

    private final CropType cropType;

    private final BigDecimal seedsUsed;

    private final BigDecimal fuelUsed;

    private final BigDecimal yield;

    private final BigDecimal fertilizersUsed;

    private final BigDecimal totalSalary;

}
