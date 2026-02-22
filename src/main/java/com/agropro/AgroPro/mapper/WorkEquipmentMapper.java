package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.WorkEquipment;

public class WorkEquipmentMapper {

    public static WorkEquipment toModel(Long workId, Long equipmentId) {
        return WorkEquipment.builder()
                .workId(workId)
                .equipmentId(equipmentId)
                .build();
    }

}
