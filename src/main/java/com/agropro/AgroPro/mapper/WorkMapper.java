package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.view.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class WorkMapper {

    public static Work toModel(WorkForm fieldWorkForm) {
        return Work.builder()
                .fieldId(fieldWorkForm.getFieldId())
                .workType(WorkType.fromString(fieldWorkForm.getWorkType()))
                .description(StringUtils.defaultIfBlank(fieldWorkForm.getDescription(), "Нет описания"))
                .startDate(fieldWorkForm.getStartDate())
                .status(WorkStatus.PLANNED)
                .endDate(fieldWorkForm.getEndDate())
                .build();
    }

    public static WorkView toView(Work work, Field field, List<EmployeeBasicInfoView> employees,
                                  List<MachineryBasicInfoView> machineries, List<EquipmentBasicInfoView> equipment) {
        return WorkView.builder()
                .id(work.getId())
                .workType(work.getWorkType().getWorkType())
                .status(work.getStatus().getWorkStatus())
                .fieldNumber(field.getFieldNumber())
                .description(work.getDescription())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .employees(employees)
                .machineries(machineries)
                .equipment(equipment)
                .build();
    }

    public static WorkBasicInfoView toBasicInfoView(Work work, Field field) {
        return WorkBasicInfoView.builder()
                .id(work.getId())
                .workType(work.getWorkType().getWorkType())
                .fieldNumber(field.getFieldNumber())
                .status(work.getStatus().getWorkStatus())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .build();
    }

}
