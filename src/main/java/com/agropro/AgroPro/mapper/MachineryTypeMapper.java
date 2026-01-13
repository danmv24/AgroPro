package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.MachineryType;
import com.agropro.AgroPro.view.MachineryTypeView;

public class MachineryTypeMapper {

    public static MachineryTypeView toView(MachineryType machineryType) {
        return MachineryTypeView.builder()
                .id(machineryType.getId())
                .machineryType(machineryType.getMachineryType())
                .build();
    }

}
