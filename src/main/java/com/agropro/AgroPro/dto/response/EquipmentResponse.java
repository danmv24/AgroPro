package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.EquipmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class EquipmentResponse {

    private final String equipmentName;

    private final EquipmentType equipmentType;

    private final Integer inventoryNumber;

    private final String statusCode;

}
