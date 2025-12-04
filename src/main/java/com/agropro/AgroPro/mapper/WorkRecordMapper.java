package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.model.WorkRecord;

public class WorkRecordMapper {

    public static WorkRecord toModel(WorkRecordForm workRecordForm) {
        return WorkRecord.builder()
                .employeeId(workRecordForm.getEmployeeId())
                .workDate(workRecordForm.getWorkDate())
                .hoursWorked(workRecordForm.getHoursWorked())
                .build();
    }

}
