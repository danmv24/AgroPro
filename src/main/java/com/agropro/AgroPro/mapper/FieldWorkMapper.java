package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.model.FieldWork;
import com.agropro.AgroPro.view.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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

    public static FieldWorkView toView(FieldWorkDetail fieldWorkDetail, List<EmployeeBasicInfoView> employees,
                                       List<MachineryBasicInfoView> machineries, List<EquipmentBasicInfoView> equipment) {
        return FieldWorkView.builder()
                .fieldWorkId(fieldWorkDetail.getFieldWorkId())
                .workTypeName(fieldWorkDetail.getWorkTypeName())
                .status(FieldWorkStatus.valueOf(fieldWorkDetail.getStatus()))
                .fieldNumber(fieldWorkDetail.getFieldNumber())
                .description(fieldWorkDetail.getDescription())
                .startDate(fieldWorkDetail.getStartDate())
                .endDate(fieldWorkDetail.getEndDate())
                .employees(employees)
                .machineries(machineries)
                .equipment(equipment)
                .build();
    }

}
