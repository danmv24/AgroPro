package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.response.*;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.Work;
import org.apache.commons.lang3.StringUtils;

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

    public static WorkResponse toResponse(Work work, Field field, List<EmployeeBasicInfoResponse> employees,
                                          List<MachineryBasicInfoResponse> machineries, List<EquipmentBasicInfoResponse> equipment,
                                          WorkResultResponse resultResponse) {
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
                .resultResponse(resultResponse)
                .build();
    }

    public static WorkBasicInfoResponse toBasicInfoView(Work work, Field field) {
        return WorkBasicInfoResponse.builder()
                .id(work.getId())
                .workType(work.getWorkType())
                .fieldNumber(field.getFieldNumber())
                .status(work.getStatus())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .build();
    }

    public static WorkResultResponse toWorkResultResponse(List<WorkMaterialUsageResponse> materials,
                                                          HarvestResponse harvestResponse) {
        return WorkResultResponse.builder()
                .materials(materials)
                .harvest(harvestResponse)
                .build();
    }


}
