package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.CropType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class CropOptimizationData {

    private final CropType cropType;

    private final BigDecimal sownArea;

    private final BigDecimal yieldPerHectare;

    private final BigDecimal costPerHectare;

}
