package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.enums.StatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MachineryResponse {

    private final Long id;

    private final String machineryName;

    private final MachineryType machineryType;

    private final Integer inventoryNumber;

    private final String licencePlate;

    private final StatusCode statusCode;

}
