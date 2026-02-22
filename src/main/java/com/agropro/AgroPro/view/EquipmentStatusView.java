package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class EquipmentStatusView {

    private final Long equipmentId;

    private final String currentStatus;

}
