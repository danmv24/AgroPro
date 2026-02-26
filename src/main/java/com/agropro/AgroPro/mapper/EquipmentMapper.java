package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;

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

    public static EquipmentView toView(Equipment equipment) {
        return EquipmentView.builder()
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .inventoryNumber(equipment.getInventoryNumber())
                .statusCode(equipment.getCurrentStatus().getStatusName())
                .build();
    }

    public static EquipmentBasicInfoView toBasicInfoView(Equipment equipment) {
        return EquipmentBasicInfoView.builder()
                .equipmentId(equipment.getId())
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .build();
    }

}
