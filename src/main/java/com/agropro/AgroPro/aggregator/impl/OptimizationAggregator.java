package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.CropOptimizationInternalData;
import com.agropro.AgroPro.dto.internal.OptimizationInternalData;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptimizationAggregator implements DataAggregator<OptimizationInternalData> {

    private final FieldPlantingRepository fieldPlantingRepository;
    private final FieldRepository fieldRepository;
    private final ProductSaleRepository productSaleRepository;
    private final WorkMaterialUsageRepository workMaterialUsageRepository;
    private final HarvestRepository harvestRepository;

    @Override
    public OptimizationInternalData collectData(LocalDate startDate, LocalDate endDate) {
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

        List<CropOptimizationInternalData> crops = buildCropOptimizationData(areaMap, yieldMap, costPerHectareMap);

        return OptimizationDataMapper.toOptimizationData(crops, productSales, totalFieldArea);
    }

    private List<CropOptimizationInternalData> buildCropOptimizationData(Map<CropType, BigDecimal> cropAreaMap,
                                                                         Map<CropType, BigDecimal> cropYieldMap,
                                                                         Map<CropType, BigDecimal> costPerHectareMap) {
        return cropAreaMap.keySet()
                .stream()
                .map(cropType -> CropOptimizationDataMapper.toCropOptimizationInternalData(
                                cropType,
                                cropAreaMap.getOrDefault(cropType, BigDecimal.ZERO),
                                cropYieldMap.getOrDefault(cropType, BigDecimal.ZERO),
                                costPerHectareMap.getOrDefault(cropType, BigDecimal.ZERO)))
                .toList();
    }

    private Map<CropType, BigDecimal> buildAreaMap(List<CropSownArea> cropSownAreas) {
        return cropSownAreas.stream()
                .collect(Collectors.toMap(
                        CropSownArea::getCropType,
                        CropSownArea::getSownArea));
    }

    private Map<CropType, BigDecimal> buildHarvestMap(List<CropHarvest> cropHarvests) {
        return cropHarvests.stream()
                .collect(Collectors.toMap(
                        CropHarvest::getCropType,
                        CropHarvest::getTotalGrossHarvest));
    }

    private Map<CropType, BigDecimal> calculateYieldMap(Map<CropType, BigDecimal> areaMap, Map<CropType, BigDecimal> harvestMap) {
        Map<CropType, BigDecimal> result = new EnumMap<>(CropType.class);

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
        Map<CropType, BigDecimal> result = new EnumMap<>(CropType.class);

        for (CropMaterialCost cost : costs) {
            if (cost.getCropType() == null) continue;

            BigDecimal value = Optional.ofNullable(cost.getTotalCost()).orElse(BigDecimal.ZERO);
            result.merge(cost.getCropType(), value, BigDecimal::add);
        }

        return result;
    }

    private Map<CropType, BigDecimal> calculateCostPerHectareMap(Map<CropType, BigDecimal> areaMap, Map<CropType, BigDecimal> costMap) {
        Map<CropType, BigDecimal> result = new EnumMap<>(CropType.class);

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
