package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.CropOptimizationInternalData;
import com.agropro.AgroPro.enums.CropType;

import java.math.BigDecimal;

public class CropOptimizationDataMapper {

    private CropOptimizationDataMapper() {
    }

    public static CropOptimizationInternalData toCropOptimizationInternalData(CropType cropType, BigDecimal sownArea,
                                                                              BigDecimal yieldPerHectare,
                                                                              BigDecimal costPerHectare) {
        return CropOptimizationInternalData.builder()
                .cropType(cropType)
                .sownArea(sownArea)
                .yieldPerHectare(yieldPerHectare != null ? yieldPerHectare : BigDecimal.ZERO)
                .costPerHectare(costPerHectare != null ? costPerHectare : BigDecimal.ZERO)
                .build();
    }

}
