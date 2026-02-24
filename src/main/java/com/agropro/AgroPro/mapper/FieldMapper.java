package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.view.FieldBasicView;
import com.agropro.AgroPro.view.FieldView;

public class FieldMapper {

    public static FieldBasicView toBasicView(Field field) {
        return FieldBasicView.builder()
                .fieldId(field.getId())
                .fieldNumber(field.getFieldNumber())
                .build();
    }

    public static FieldView toView(Field field, FieldPlanting fieldPlanting) {
        return FieldView.builder()
                .id(field.getId())
                .fieldNumber(field.getFieldNumber())
                .cropType(fieldPlanting.getCropType().getCropType())
                .area(field.getArea())
                .build();
    }

}
