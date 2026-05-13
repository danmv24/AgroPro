package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.CropOptimizationData;
import com.agropro.AgroPro.enums.CropType;

import java.math.BigDecimal;

public class CropOptimizationDataMapper {

    public static CropOptimizationData toCropOptimizationData(CropType cropType, BigDecimal sownArea, BigDecimal yieldPerHectare,
                                                              BigDecimal costPerHectare) {
        return CropOptimizationData.builder()
                .cropType(cropType)
                .sownArea(sownArea)
                .yieldPerHectare(yieldPerHectare != null ? yieldPerHectare : BigDecimal.ZERO)
                .costPerHectare(costPerHectare != null ? costPerHectare : BigDecimal.ZERO)
                .build();
    }

}
