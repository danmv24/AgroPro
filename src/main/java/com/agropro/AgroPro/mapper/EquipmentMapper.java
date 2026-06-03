package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.EquipmentRequest;
import com.agropro.AgroPro.dto.response.EquipmentBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EquipmentResponse;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.model.Equipment;

public class EquipmentMapper {

    private EquipmentMapper() {
    }

    public static Equipment toModel(EquipmentRequest equipmentRequest) {
        return Equipment.builder()
                .equipmentName(equipmentRequest.getEquipmentName())
                .equipmentType(equipmentRequest.getEquipmentType())
                .inventoryNumber(equipmentRequest.getInventoryNumber())
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(equipmentRequest.getPurchaseDate())
                .build();
    }

    public static EquipmentResponse toResponse(Equipment equipment) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .inventoryNumber(equipment.getInventoryNumber())
                .statusCode(equipment.getCurrentStatus().getStatusName())
                .build();
    }

    public static EquipmentBasicInfoResponse toBasicInfoResponse(Equipment equipment) {
        return EquipmentBasicInfoResponse.builder()
                .equipmentId(equipment.getId())
                .equipmentName(equipment.getEquipmentName())
                .inventoryNumber(equipment.getInventoryNumber())
                .build();
    }

}
