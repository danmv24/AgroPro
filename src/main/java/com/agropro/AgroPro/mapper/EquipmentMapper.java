package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.model.Equipment;

public class EquipmentMapper {

    public static Equipment toModel(EquipmentForm equipmentForm, Long idleStatusId) {
        return Equipment.builder()
                .equipmentName(equipmentForm.getEquipmentName())
                .equipmentTypeId(equipmentForm.getEquipmentTypeId())
                .inventoryNumber(equipmentForm.getInventoryNumber())
                .currentStatusId(idleStatusId)
                .purchaseDate(equipmentForm.getPurchaseDate())
                .build();
    }

}
