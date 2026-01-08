package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.model.Machinery;

public class MachineryMapper {

    public static Machinery toModel(MachineryForm machineryForm, Long idleStatusId) {
        return Machinery.builder()
                .machineryName(machineryForm.getMachineryName())
                .inventoryNumber(machineryForm.getInventoryNumber())
                .machineryTypeId(machineryForm.getMachineryTypeId())
                .licencePlate(machineryForm.getLicensePlate())
                .statusId(idleStatusId)
                .build();
    }

}
