package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.CropOptimizationData;
import com.agropro.AgroPro.dto.internal.OptimizationData;
import com.agropro.AgroPro.projection.ProductSaleStatistic;

import java.math.BigDecimal;
import java.util.List;

public class OptimizationDataMapper {

    public static OptimizationData toOptimizationData(List<CropOptimizationData> crops, List<ProductSaleStatistic> productSales,
                                                      BigDecimal totalFieldArea) {
        return OptimizationData.builder()
                .totalFieldArea(totalFieldArea)
                .crops(crops)
                .productSales(productSales)
                .build();
    }

}
