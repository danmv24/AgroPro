package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.EquipmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EquipmentTypeCount {

    private final EquipmentType equipmentType;

    private final int count;

}
