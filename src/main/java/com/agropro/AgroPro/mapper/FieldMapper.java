package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.FieldPlanting;

public class FieldMapper {

    public static FieldBasicInfoResponse toBasicView(Field field) {
        return FieldBasicInfoResponse.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .build();
    }

    public static FieldResponse toView(Field field, FieldPlanting fieldPlanting) {
        return FieldResponse.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .cropType(fieldPlanting != null ? fieldPlanting.getCropType().getCropTypeName() : null)
                .area(field.getArea())
                .build();
    }

}
