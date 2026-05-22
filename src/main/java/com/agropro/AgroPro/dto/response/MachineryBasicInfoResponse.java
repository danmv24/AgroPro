package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MachineryBasicInfoResponse {

    private final Long machineryId;

    private final Integer inventoryNumber;

    private final String machineryName;

    private final String licensePlate;

}
