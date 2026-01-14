package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class EquipmentTypeView {

    private final Long id;

    private final String equipmentType;

}
