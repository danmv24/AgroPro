package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropHarvest;
import com.agropro.AgroPro.projection.CropLaborCost;
import com.agropro.AgroPro.projection.CropMaterialCost;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class ApkNineReportData {

    private final List<CropArea> cropAreas;

    private final List<CropHarvest> cropHarvests;

    private final List<CropMaterialCost> cropMaterialCosts;
    
    private final List<CropLaborCost> cropLaborCosts;

}
