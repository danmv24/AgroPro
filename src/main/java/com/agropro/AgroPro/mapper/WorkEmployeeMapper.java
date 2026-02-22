package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.WorkEmployee;

public class WorkEmployeeMapper {

    public static WorkEmployee toModel(Long workId, Long employeeId) {
        return WorkEmployee.builder()
                .workId(workId)
                .employeeId(employeeId)
                .build();
    }

}
