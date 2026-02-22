package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.WorkMachinery;

public class WorkMachineryMapper {

    public static WorkMachinery toModel(Long workId, Long machineryId) {
        return WorkMachinery.builder()
                .workId(workId)
                .machineryId(machineryId)
                .build();
    }

}
