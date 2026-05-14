package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.CropType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class CropLaborCost {

    private final CropType cropType;

    private final BigDecimal totalSalary;

}
