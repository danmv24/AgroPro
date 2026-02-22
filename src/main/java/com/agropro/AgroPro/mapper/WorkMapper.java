package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.form.WorkForm;
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

    public static WorkView toView(FieldWorkDetail fieldWorkDetail, List<EmployeeBasicInfoView> employees,
                                  List<MachineryBasicInfoView> machineries, List<EquipmentBasicInfoView> equipment) {
        return WorkView.builder()
                .fieldWorkId(fieldWorkDetail.getFieldWorkId())
                .workType(fieldWorkDetail.getWorkType())
                .status(fieldWorkDetail.getStatus())
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
