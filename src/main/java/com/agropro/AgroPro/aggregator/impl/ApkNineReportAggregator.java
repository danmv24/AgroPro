package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.ApkNineReportData;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropHarvest;
import com.agropro.AgroPro.projection.CropLaborCost;
import com.agropro.AgroPro.projection.CropMaterialCost;
import com.agropro.AgroPro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApkNineReportAggregator implements DataAggregator<ApkNineReportData> {

    private final FieldPlantingRepository fieldPlantingRepository;

    private final WorkRepository workRepository;

    private final HarvestRepository harvestRepository;

    private final WorkMaterialUsageRepository workMaterialUsageRepository;

    private final WorkEmployeeRepository workEmployeeRepository;

    @Override
    public ApkNineReportData collectData(LocalDate startDate, LocalDate endDate) {
//        List<CropStatistic> cropStatistics = collectCropStatistic(startDate, endDate);

        // новое
        List<CropArea> cropAreas = collectCropAreas(startDate, endDate);
        List<CropHarvest> cropHarvests = collectCropHarvestStatisticNew(startDate, endDate);
        List<CropMaterialCost> cropMaterialCosts = collectCropMaterialCost(startDate, endDate);
        List<CropLaborCost> cropLaborCosts = collectCropLaborCost(startDate, endDate);

        return ReportDataMapper.toNineReportData(cropAreas, cropHarvests, cropMaterialCosts, cropLaborCosts);
    }

    private List<CropLaborCost> collectCropLaborCost(LocalDate startDate, LocalDate endDate) {
        return workEmployeeRepository.findCropLaborCostsByDateRange(startDate, endDate);
    }

//    private List<CropStatistic> collectCropStatistic(LocalDate startDate, LocalDate endDate) {
//        return workRepository.findCropStatisticsByPeriod(startDate, endDate);
//    }

    private List<CropMaterialCost> collectCropMaterialCost(LocalDate startDate, LocalDate endDate) {
        return workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(startDate, endDate);
    }

    private List<CropArea> collectCropAreas(LocalDate startDate, LocalDate endDate) {
        return fieldPlantingRepository.findSownAndHarvestedAreas(startDate, endDate);
    }

    private List<CropHarvest> collectCropHarvestStatisticNew(LocalDate startDate, LocalDate endDate) {
        return harvestRepository.findTotalHarvestByCropTypeAndDateRange(startDate, endDate);
    }

}
