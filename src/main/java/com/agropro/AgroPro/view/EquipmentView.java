package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class EquipmentView {

    private final String equipmentName;

    private final String equipmentType;

    private final Integer inventoryNumber;

    private final String statusCode;

}
