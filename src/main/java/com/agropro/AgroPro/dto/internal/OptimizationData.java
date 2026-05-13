package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.projection.ProductSaleStatistic;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class OptimizationData {

    private final List<CropOptimizationData> crops;

    private final List<ProductSaleStatistic> productSales;

    private final BigDecimal totalFieldArea;

}
