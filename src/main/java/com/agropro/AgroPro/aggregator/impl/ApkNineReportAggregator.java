package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.ReportDataAggregator;
import com.agropro.AgroPro.dto.internal.ApkNineReportData;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropStatistic;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApkNineReportAggregator implements ReportDataAggregator<ApkNineReportData> {

    private final FieldPlantingRepository fieldPlantingRepository;

    private final WorkRepository workRepository;

    @Override
    public ApkNineReportData collectData(LocalDate startDate, LocalDate endDate) {
        List<CropStatistic> cropStatistics = collectCropStatistic(startDate, endDate);
        List<CropArea> cropAreas = collectCropAreas(startDate, endDate);

        return ReportDataMapper.toNineReportData(cropAreas, cropStatistics);
    }

    private List<CropStatistic> collectCropStatistic(LocalDate startDate, LocalDate endDate) {
        return workRepository.findCropStatisticsByPeriod(startDate, endDate);
    }

    private List<CropArea> collectCropAreas(LocalDate startDate, LocalDate endDate) {
        return fieldPlantingRepository.findSownAndHarvestedAreas(startDate, endDate);
    }

}
