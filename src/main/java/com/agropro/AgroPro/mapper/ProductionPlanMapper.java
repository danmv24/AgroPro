package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.response.ProductionPlanResponse;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.enums.Product;
import com.agropro.AgroPro.model.ProductionPlan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class ProductionPlanMapper {

    public static ProductionPlanResponse toResponse(ProductionPlan plan) {
        return ProductionPlanResponse.builder()
                .id(plan.getId())
                .winterWheatArea(plan.getWinterWheatArea())
                .springWheatArea(plan.getSpringWheatArea())
                .springBarleyArea(plan.getSpringBarleyArea())
                .sunflowerArea(plan.getSunflowerArea())
                .appleArea(plan.getAppleArea())
                .winterWheatQuantitySale(plan.getWinterWheatQuantitySale())
                .springWheatQuantitySale(plan.getSpringWheatQuantitySale())
                .springBarleyQuantitySale(plan.getSpringBarleyQuantitySale())
                .sunflowerQuantitySale(plan.getSunflowerQuantitySale())
                .appleQuantitySale(plan.getAppleQuantitySale())
                .totalCost(plan.getTotalCost())
                .totalRevenue(plan.getTotalRevenue())
                .maxProfit(plan.getMaxProfit())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .createdAt(plan.getCreatedAt())
                .build();
    }

    public static ProductionPlan toModel(Map<Product, BigDecimal> saleResult, Map<CropType, BigDecimal> areaResult,
                                         BigDecimal totalCost, BigDecimal totalRevenue, BigDecimal maxProfit,
                                         LocalDate startDate, LocalDate endDate, LocalDateTime now) {
        return ProductionPlan.builder()
                .winterWheatArea(areaResult.getOrDefault(CropType.WINTER_WHEAT, BigDecimal.ZERO))
                .springWheatArea(areaResult.getOrDefault(CropType.SPRING_WHEAT, BigDecimal.ZERO))
                .springBarleyArea(areaResult.getOrDefault(CropType.SPRING_BARLEY, BigDecimal.ZERO))
                .sunflowerArea(areaResult.getOrDefault(CropType.SUNFLOWER, BigDecimal.ZERO))
                .appleArea(areaResult.getOrDefault(CropType.APPLE, BigDecimal.ZERO))
                .winterWheatQuantitySale(saleResult.getOrDefault(Product.WINTER_WHEAT, BigDecimal.ZERO))
                .springWheatQuantitySale(saleResult.getOrDefault(Product.SPRING_WHEAT, BigDecimal.ZERO))
                .springBarleyQuantitySale(saleResult.getOrDefault(Product.SPRING_BARLEY, BigDecimal.ZERO))
                .sunflowerQuantitySale(saleResult.getOrDefault(Product.SUNFLOWER, BigDecimal.ZERO))
                .appleQuantitySale(saleResult.getOrDefault(Product.APPLE, BigDecimal.ZERO))
                .totalCost(totalCost)
                .totalRevenue(totalRevenue)
                .maxProfit(maxProfit)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(now)
                .build();
    }

}
