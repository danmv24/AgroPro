package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.model.FieldPlanting;

public class FieldPlantingMapper {

    public static FieldPlanting toModel(FieldPlantingForm fieldPlantingForm) {
        return FieldPlanting.builder()
                .fieldId(fieldPlantingForm.getFieldId())
                .cropId(fieldPlantingForm.getCropId())
                .plantingYear(fieldPlantingForm.getPlantingYear())
                .build();
    }

}
