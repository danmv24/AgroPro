package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MachineryBasicInfoView {

    private final Long machineryId;

    private final String machineryType;

    private final String machineryName;

}
