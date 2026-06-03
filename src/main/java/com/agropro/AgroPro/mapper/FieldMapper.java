package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.FieldInternalData;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.model.Field;

public class FieldMapper {

    private FieldMapper() {
    }

    public static FieldBasicInfoResponse toBasicInfoResponse(Field field) {
        return FieldBasicInfoResponse.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .build();
    }

    public static FieldResponse toResponse(Field field, FieldPlantingInternalData fieldPlanting) {
        return FieldResponse.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .cropType(fieldPlanting != null ? fieldPlanting.getCropType() : null)
                .area(field.getArea())
                .build();
    }

    public static FieldInternalData toInternalData(Field field) {
        return FieldInternalData.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .area(field.getArea())
                .build();
    }
}
