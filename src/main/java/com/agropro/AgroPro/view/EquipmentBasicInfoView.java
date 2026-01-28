package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class EquipmentBasicInfoView {

    private final Long equipmentId;

    private final String equipmentName;

    private final String equipmentType;

}
