package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.EquipmentType;
import com.agropro.AgroPro.view.EquipmentTypeView;

public class EquipmentTypeMapper {

    public static EquipmentTypeView toView(EquipmentType equipmentType) {
        return EquipmentTypeView.builder()
                .id(equipmentType.getId())
                .equipmentType(equipmentType.getEquipmentType())
                .build();
    }

}
