package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.CropOptimizationData;
import com.agropro.AgroPro.dto.internal.OptimizationData;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.mapper.CropOptimizationDataMapper;
import com.agropro.AgroPro.mapper.OptimizationDataMapper;
import com.agropro.AgroPro.projection.CropHarvest;
import com.agropro.AgroPro.projection.CropMaterialCost;
import com.agropro.AgroPro.projection.CropSownArea;
import com.agropro.AgroPro.projection.ProductSaleStatistic;
import com.agropro.AgroPro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptimizationAggregator implements DataAggregator<OptimizationData> {

    private final FieldPlantingRepository fieldPlantingRepository;
    private final FieldRepository fieldRepository;
    private final ProductSaleRepository productSaleRepository;
    private final WorkMaterialUsageRepository workMaterialUsageRepository;
    private final HarvestRepository harvestRepository;

    @Override
    public OptimizationData collectData(LocalDate startDate, LocalDate endDate) {
        List<CropSownArea> cropSownAreas = fieldPlantingRepository.findSownArea(startDate, endDate);
        BigDecimal totalFieldArea = fieldRepository.sumAllFieldsArea();
        List<CropMaterialCost> cropMaterialCosts = workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(startDate, endDate);
        List<CropHarvest> cropHarvests = harvestRepository.findTotalHarvestByCropTypeAndDateRange(startDate, endDate);
        List<ProductSaleStatistic> productSales = productSaleRepository.findProductSalesSummaryBySaleDateBetween(startDate, endDate);

        Map<CropType, BigDecimal> areaMap = buildAreaMap(cropSownAreas);
        Map<CropType, BigDecimal> harvestMap = buildHarvestMap(cropHarvests);
        Map<CropType, BigDecimal> yieldMap = calculateYieldMap(areaMap, harvestMap);
        Map<CropType, BigDecimal> totalCostMap = buildTotalCostMap(cropMaterialCosts);
        Map<CropType, BigDecimal> costPerHectareMap = calculateCostPerHectareMap(areaMap, totalCostMap);

        List<CropOptimizationData> crops = buildCropOptimizationData(areaMap, yieldMap, costPerHectareMap);

        return OptimizationDataMapper.toOptimizationData(crops, productSales, totalFieldArea);
    }

    private List<CropOptimizationData> buildCropOptimizationData(Map<CropType, BigDecimal> cropAreaMap,
                                                                 Map<CropType, BigDecimal> cropYieldMap,
                                                                 Map<CropType, BigDecimal> costPerHectareMap) {
        return cropAreaMap.keySet()
                .stream()
                .map(cropType -> CropOptimizationDataMapper.toCropOptimizationData(
                                cropType,
                                cropAreaMap.getOrDefault(cropType, BigDecimal.ZERO),
                                cropYieldMap.getOrDefault(cropType, BigDecimal.ZERO),
                                costPerHectareMap.getOrDefault(cropType, BigDecimal.ZERO)))
                .toList();
    }

    private Map<CropType, BigDecimal> buildAreaMap(List<CropSownArea> list) {
        return list.stream()
                .collect(Collectors.toMap(
                        CropSownArea::getCropType,
                        CropSownArea::getSownArea
                ));
    }

    private Map<CropType, BigDecimal> buildHarvestMap(List<CropHarvest> list) {
        return list.stream()
                .collect(Collectors.toMap(
                        CropHarvest::getCropType,
                        CropHarvest::getTotalGrossHarvest
                ));
    }

    private Map<CropType, BigDecimal> calculateYieldMap(Map<CropType, BigDecimal> areaMap, Map<CropType, BigDecimal> harvestMap) {
        Map<CropType, BigDecimal> result = new HashMap<>();

        for (CropType cropType : areaMap.keySet()) {
            BigDecimal area = areaMap.getOrDefault(cropType, BigDecimal.ZERO);
            BigDecimal harvest = harvestMap.getOrDefault(cropType, BigDecimal.ZERO);

            if (area.compareTo(BigDecimal.ZERO) > 0) {
                result.put(cropType, harvest.divide(area, 2, RoundingMode.HALF_UP));
            }
        }

        return result;
    }

    private Map<CropType, BigDecimal> buildTotalCostMap(List<CropMaterialCost> costs) {
        Map<CropType, BigDecimal> result = new HashMap<>();

        for (CropMaterialCost cost : costs) {
            if (cost.getCropType() == null) continue;

            BigDecimal value = Optional.ofNullable(cost.getTotalCost())
                    .orElse(BigDecimal.ZERO);

            result.merge(cost.getCropType(), value, BigDecimal::add);
        }

        return result;
    }

    private Map<CropType, BigDecimal> calculateCostPerHectareMap(Map<CropType, BigDecimal> areaMap, Map<CropType, BigDecimal> costMap) {
        Map<CropType, BigDecimal> result = new HashMap<>();

        for (CropType cropType : areaMap.keySet()) {
            BigDecimal area = areaMap.getOrDefault(cropType, BigDecimal.ZERO);
            BigDecimal cost = costMap.getOrDefault(cropType, BigDecimal.ZERO);

            if (area.compareTo(BigDecimal.ZERO) > 0) {
                result.put(cropType, cost.divide(area, 2, RoundingMode.HALF_UP));
            }
        }

        return result;
    }
}


//@Component
//@RequiredArgsConstructor
//public class OptimizationAggregator implements DataAggregator<OptimizationData> {
//
//    private final FieldPlantingRepository fieldPlantingRepository;
//
//    private final FieldRepository fieldRepository;
//
//    private final ProductSaleRepository productSaleRepository;
//
//    private final WorkMaterialUsageRepository workMaterialUsageRepository;
//
//    private final HarvestRepository harvestRepository;
//
//    @Override
//    public OptimizationData collectData(LocalDate startDate, LocalDate endDate) {
//        List<CropSownArea> cropSownAreas = fieldPlantingRepository.findSownArea(startDate, endDate); // га под каждой культурой
//        BigDecimal totalFieldArea = fieldRepository.sumAllFieldsArea(); // баланс пашни
//        List<ProductSaleStatistic> productSalesStatistics = productSaleRepository.findProductSalesSummaryBySaleDateBetween(startDate, endDate); // реализация
//        List<CropMaterialCost> cropMaterialCosts = workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(startDate, endDate); // стоимость каждой культуры
//        List<CropHarvest> cropHarvests = harvestRepository.findTotalHarvestByCropTypeAndDateRange(startDate, endDate); // валовый сбор каждой культуры
//
//        Map<CropType, BigDecimal> cropHarvestMap = buildHarvestMap(cropHarvests);
//        Map<CropType, BigDecimal> cropAreaMap = buildAreaMap(cropSownAreas);
//        Map<CropType, BigDecimal> cropYieldMap = calculateYieldMap(cropAreaMap, cropHarvestMap);
//        Map<CropType, BigDecimal> totalCostMap = buildTotalCostMap(cropMaterialCosts);
//        Map<CropType, BigDecimal> costPerHectareMap = calculateCostPerHectareMap(cropAreaMap, totalCostMap);
//        Map<Product, ProductSaleStatistic> salesMap = buildSalesMap(productSalesStatistics);
//
//        List<CropOptimizationData> crops = buildCropOptimizationData(cropAreaMap, cropYieldMap, costPerHectareMap, salesMap);
//
//        return OptimizationDataMapper.toOptimizationData(crops, totalFieldArea);
//    }
//
//    private List<CropOptimizationData> buildCropOptimizationData(Map<CropType, BigDecimal> cropAreaMap,
//                                                                 Map<CropType, BigDecimal> cropYieldMap,
//                                                                 Map<CropType, BigDecimal> costPerHectareMap,
//                                                                 Map<Product, ProductSaleStatistic> salesMap) {
//        return cropAreaMap.keySet()
//                .stream()
//                .map(cropType -> {
//                    Product product = Product.valueOf(cropType.name());
//                    ProductSaleStatistic sale = salesMap.get(product);
//
//                    return CropOptimizationDataMapper.toCropOptimizationData(cropType, cropAreaMap.get(cropType),
//                                    cropYieldMap.get(cropType), costPerHectareMap.get(cropType), sale);
//                })
//                .toList();
//    }
//
//    private Map<Product, ProductSaleStatistic> buildSalesMap(List<ProductSaleStatistic> productSalesStatistics) {
//        return productSalesStatistics.stream()
//                .collect(Collectors.toMap(
//                        ProductSaleStatistic::getProduct,
//                        Function.identity()
//                ));
//    }
//
//    private Map<CropType, BigDecimal> calculateCostPerHectareMap(Map<CropType, BigDecimal> cropAreaMap, Map<CropType, BigDecimal> totalCostMap) {
//        Map<CropType, BigDecimal> result = new HashMap<>();
//
//        for (CropType cropType : cropAreaMap.keySet()) {
//            BigDecimal area = cropAreaMap.get(cropType);
//            BigDecimal totalCost = totalCostMap.getOrDefault(cropType, BigDecimal.ZERO);
//
//            if (area.compareTo(BigDecimal.ZERO) > 0) {
//                result.put(cropType, totalCost.divide(area, 2, RoundingMode.HALF_UP));
//            }
//        }
//
//        return result;
//    }
//
//    private Map<CropType, BigDecimal> buildTotalCostMap(
//            List<CropMaterialCost> cropMaterialCosts
//    ) {
//
//        Map<CropType, BigDecimal> result = new HashMap<>();
//
//        for (CropMaterialCost cost : cropMaterialCosts) {
//
//            if (cost.getCropType() == null) {
//                continue;
//            }
//
//            BigDecimal value =
//                    Optional.ofNullable(cost.getTotalCost())
//                            .orElse(BigDecimal.ZERO);
//
//            result.merge(
//                    cost.getCropType(),
//                    value,
//                    BigDecimal::add
//            );
//        }
//
//        return result;
//    }
//
//    private Map<CropType, BigDecimal> calculateYieldMap(Map<CropType, BigDecimal> cropAreaMap, Map<CropType, BigDecimal> cropHarvestMap) {
//        Map<CropType, BigDecimal> result = new HashMap<>();
//
//        for (CropType cropType : cropAreaMap.keySet()) {
//            BigDecimal area = cropAreaMap.getOrDefault(cropType, BigDecimal.ZERO);
//            BigDecimal harvest = cropHarvestMap.getOrDefault(cropType, BigDecimal.ZERO);
//
//            if (area.compareTo(BigDecimal.ZERO) > 0) {
//                result.put(cropType, harvest.divide(area, 2, RoundingMode.HALF_UP));
//            }
//        }
//
//        return result;
//    }
//
//    private Map<CropType, BigDecimal> buildAreaMap(List<CropSownArea> cropSownAreas) {
//        return cropSownAreas.stream()
//                .collect(Collectors.toMap(
//                        CropSownArea::getCropType,
//                        CropSownArea::getSownArea
//                ));
//    }
//
//    private Map<CropType, BigDecimal> buildHarvestMap(List<CropHarvest> cropHarvests) {
//        return cropHarvests.stream()
//                .collect(Collectors.toMap(
//                        CropHarvest::getCropType,
//                        CropHarvest::getTotalGrossHarvest
//                ));
//    }
//
//
//
//}
