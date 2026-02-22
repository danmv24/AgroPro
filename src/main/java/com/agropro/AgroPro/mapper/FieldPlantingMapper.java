package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.model.FieldPlanting;

import java.time.LocalDate;

public class FieldPlantingMapper {

    public static FieldPlanting toModel(Long fieldId, CropType cropType, LocalDate plantingDate) {
        return FieldPlanting.builder()
                .fieldId(fieldId)
                .cropType(cropType)
                .plantingDate(plantingDate)
                .build();
    }

//    public static FieldPlantingView toView(FieldPlanting fieldPlanting) {
//        return FieldPlantingView.builder()
//                .cropName(fieldPlanting.getCropType().getCropType())
//                .plantingYear(fieldPlanting.getPlantingYear())
//                .build();
//    }

}
