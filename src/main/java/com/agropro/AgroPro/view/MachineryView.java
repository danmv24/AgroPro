package com.agropro.AgroPro.view;

import com.agropro.AgroPro.enums.MachineryType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MachineryView {

    private final String machineryName;

    private final MachineryType machineryType;

    private final Integer inventoryNumber;

    private final String licencePlate;

    private final String statusCode;

}
