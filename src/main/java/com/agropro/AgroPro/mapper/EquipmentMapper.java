package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.EquipmentForm;
import com.agropro.AgroPro.dto.response.EquipmentBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EquipmentResponse;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.model.Equipment;

public class EquipmentMapper {

    public static Equipment toModel(EquipmentForm equipmentForm) {
        return Equipment.builder()
                .equipmentName(equipmentForm.getEquipmentName())
                .equipmentType(equipmentForm.getEquipmentType())
                .inventoryNumber(equipmentForm.getInventoryNumber())
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(equipmentForm.getPurchaseDate())
                .build();
    }

    public static EquipmentResponse toView(Equipment equipment) {
        return EquipmentResponse.builder()
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .inventoryNumber(equipment.getInventoryNumber())
                .statusCode(equipment.getCurrentStatus().getStatusName())
                .build();
    }

    public static EquipmentBasicInfoResponse toBasicInfoView(Equipment equipment) {
        return EquipmentBasicInfoResponse.builder()
                .equipmentId(equipment.getId())
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .build();
    }

}
