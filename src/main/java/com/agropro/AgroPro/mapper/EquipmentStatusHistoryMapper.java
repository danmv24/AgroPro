package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.model.EquipmentStatusHistory;

import java.time.LocalDateTime;

public class EquipmentStatusHistoryMapper {

    public static EquipmentStatusHistory toModel(Equipment equipment, LocalDateTime changedAt) {
        return EquipmentStatusHistory.builder()
                .equipmentId(equipment.getId())
                .status(equipment.getCurrentStatus())
                .changedAt(changedAt)
                .build();
    }

}
