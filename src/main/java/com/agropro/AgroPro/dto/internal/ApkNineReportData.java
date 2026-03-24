package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropStatistic;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class ApkNineReportData {

    private final List<CropArea> cropAreas;

    private final List<CropStatistic> cropStatistics;

}
