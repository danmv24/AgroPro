package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.view.FieldView;

public class FieldMapper {

    public static FieldView toView(Field field) {
        return FieldView.builder()
                .fieldId(field.getId())
                .fieldNumber(field.getFieldNumber())
                .build();
    }

}
