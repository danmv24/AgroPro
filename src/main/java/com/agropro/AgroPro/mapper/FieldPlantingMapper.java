package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.controller.FieldPlantingResponse;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.model.FieldPlanting;

import java.time.LocalDate;

public class FieldPlantingMapper {

    private FieldPlantingMapper() {
    }

    public static FieldPlanting toModel(Long fieldId, CropType crop, LocalDate plantingDate) {
        return FieldPlanting.builder()
                .fieldId(fieldId)
                .cropType(crop)
                .plantingDate(plantingDate)
                .build();
    }

    public static FieldPlantingResponse toResponse(FieldPlanting fieldPlanting) {
        return FieldPlantingResponse.builder()
                .cropType(fieldPlanting.getCropType())
                .plantingDate(fieldPlanting.getPlantingDate())
                .harvestDate(fieldPlanting.getHarvestDate())
                .build();
    }

    public static FieldPlantingInternalData toInternalData(FieldPlanting fieldPlanting) {
        return FieldPlantingInternalData.builder()
                .id(fieldPlanting.getId())
                .fieldId(fieldPlanting.getFieldId())
                .cropType(fieldPlanting.getCropType())
                .plantingDate(fieldPlanting.getPlantingDate())
                .harvestDate(fieldPlanting.getHarvestDate())
                .build();
    }

}
