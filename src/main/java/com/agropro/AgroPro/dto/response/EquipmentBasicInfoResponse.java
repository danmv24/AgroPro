package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class EquipmentBasicInfoResponse {

    private final Long equipmentId;

    private final String equipmentName;

    private final Integer inventoryNumber;

}
