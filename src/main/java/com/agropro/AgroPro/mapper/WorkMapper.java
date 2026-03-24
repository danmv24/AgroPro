package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.response.*;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.Work;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Slice;

import java.util.List;

public class WorkMapper {

    public static Work toModel(WorkRequest workRequest) {
        return Work.builder()
                .fieldId(workRequest.getFieldId())
                .workType(workRequest.getWorkType())
                .description(StringUtils.defaultIfBlank(workRequest.getDescription(), "Нет описания"))
                .startDate(workRequest.getStartDate())
                .status(WorkStatus.PLANNED)
                .endDate(workRequest.getEndDate())
                .build();
    }

    public static WorkResponse toView(Work work, Field field, List<EmployeeBasicInfoResponse> employees,
                                      List<MachineryBasicInfoResponse> machineries, List<EquipmentBasicInfoResponse> equipment) {
        return WorkResponse.builder()
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

    public static WorkBasicInfoResponse toBasicInfoView(Work work, Field field, boolean hasResult) {
        return WorkBasicInfoResponse.builder()
                .id(work.getId())
                .workType(work.getWorkType())
                .fieldNumber(field.getFieldNumber())
                .status(work.getStatus())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .hasResult(hasResult)
                .build();
    }

    public static WorkByStatusResponse toWorkByStatusView(Slice<WorkBasicInfoResponse> planned,
                                                          Slice<WorkBasicInfoResponse> inProgress,
                                                          Slice<WorkBasicInfoResponse> completed) {
        return WorkByStatusResponse.builder()
                .planned(planned)
                .inProgress(inProgress)
                .completed(completed)
                .build();
    }



}
