package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.ApkNineReportInternalData;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropHarvest;
import com.agropro.AgroPro.projection.CropLaborCost;
import com.agropro.AgroPro.projection.CropMaterialCost;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.HarvestRepository;
import com.agropro.AgroPro.repository.WorkEmployeeRepository;
import com.agropro.AgroPro.repository.WorkMaterialUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApkNineReportAggregator implements DataAggregator<ApkNineReportInternalData> {

    private final FieldPlantingRepository fieldPlantingRepository;
    private final HarvestRepository harvestRepository;

    private final WorkMaterialUsageRepository workMaterialUsageRepository;

    private final WorkEmployeeRepository workEmployeeRepository;

    @Override
    public ApkNineReportInternalData collectData(LocalDate startDate, LocalDate endDate) {
        List<CropArea> cropAreas = fieldPlantingRepository.findSownAndHarvestedAreas(startDate, endDate);
        List<CropHarvest> cropHarvests = harvestRepository.findTotalHarvestByCropTypeAndDateRange(startDate, endDate);
        List<CropMaterialCost> cropMaterialCosts = workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(startDate, endDate);
        List<CropLaborCost> cropLaborCosts = workEmployeeRepository.findCropLaborCostsByDateRange(startDate, endDate);

        return ReportDataMapper.toNineReportInternalData(cropAreas, cropHarvests, cropMaterialCosts, cropLaborCosts);
    }


}
