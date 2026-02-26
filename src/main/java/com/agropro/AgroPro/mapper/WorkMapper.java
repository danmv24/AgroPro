package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Slice;

import java.util.List;

public class WorkMapper {

    public static Work toModel(WorkForm workForm) {
        return Work.builder()
                .fieldId(workForm.getFieldId())
                .workType(workForm.getWorkType())
                .description(StringUtils.defaultIfBlank(workForm.getDescription(), "Нет описания"))
                .startDate(workForm.getStartDate())
                .status(WorkStatus.PLANNED)
                .endDate(workForm.getEndDate())
                .build();
    }

    public static WorkView toView(Work work, Field field, List<EmployeeBasicInfoView> employees,
                                  List<MachineryBasicInfoView> machineries, List<EquipmentBasicInfoView> equipment) {
        return WorkView.builder()
                .id(work.getId())
                .workType(work.getWorkType())
                .status(work.getStatus())
                .fieldNumber(field.getFieldNumber())
                .description(work.getDescription())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .employees(employees)
                .machineries(machineries)
                .equipment(equipment)
                .build();
    }

    public static WorkBasicInfoView toBasicInfoView(Work work, Field field, boolean hasResult) {
        return WorkBasicInfoView.builder()
                .id(work.getId())
                .workType(work.getWorkType())
                .fieldNumber(field.getFieldNumber())
                .status(work.getStatus())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .hasResult(hasResult)
                .build();
    }

    public static WorkByStatusView toWorkByStatusView(Slice<WorkBasicInfoView> planned,
                                                      Slice<WorkBasicInfoView> inProgress,
                                                      Slice<WorkBasicInfoView> completed) {
        return WorkByStatusView.builder()
                .planned(planned)
                .inProgress(inProgress)
                .completed(completed)
                .build();
    }



}
