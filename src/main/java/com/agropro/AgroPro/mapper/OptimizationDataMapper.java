package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.CropOptimizationInternalData;
import com.agropro.AgroPro.dto.internal.OptimizationInternalData;
import com.agropro.AgroPro.projection.ProductSaleStatistic;

import java.math.BigDecimal;
import java.util.List;

public class OptimizationDataMapper {

    private OptimizationDataMapper() {
    }

    public static OptimizationInternalData toOptimizationData(List<CropOptimizationInternalData> crops, List<ProductSaleStatistic> productSales,
                                                              BigDecimal totalFieldArea) {
        return OptimizationInternalData.builder()
                .totalFieldArea(totalFieldArea)
                .crops(crops)
                .productSales(productSales)
                .build();
    }

}
