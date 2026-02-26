package com.agropro.AgroPro.view;

import com.agropro.AgroPro.enums.MachineryType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MachineryBasicInfoView {

    private final Long machineryId;

    private final MachineryType machineryType;

    private final String machineryName;

    private final String licensePlate;

}
