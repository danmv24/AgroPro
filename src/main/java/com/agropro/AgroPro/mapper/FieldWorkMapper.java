package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.model.FieldWork;
import org.apache.commons.lang3.StringUtils;

public class FieldWorkMapper {

    public static FieldWork toModel(FieldWorkForm fieldWorkForm) {
        return FieldWork.builder()
                .fieldId(fieldWorkForm.getFieldId())
                .workTypeId(fieldWorkForm.getWorkTypeId())
                .description(StringUtils.defaultIfBlank(fieldWorkForm.getDescription(), "Нет описания"))
                .startDate(fieldWorkForm.getStartDate())
                .endDate(fieldWorkForm.getEndDate())
                .build();
    }

}
