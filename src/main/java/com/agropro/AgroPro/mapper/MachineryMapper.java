package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

public class MachineryMapper {

    public static Machinery toModel(MachineryForm machineryForm) {
        return Machinery.builder()
                .machineryName(machineryForm.getMachineryName())
                .inventoryNumber(machineryForm.getInventoryNumber())
                .machineryType(MachineryType.fromString(machineryForm.getMachineryType()))
                .licensePlate(machineryForm.getLicensePlate())
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(machineryForm.getPurchaseDate())
                .build();
    }

    public static MachineryView toView(Machinery machinery) {
        return MachineryView.builder()
                .machineryName(machinery.getMachineryName())
                .machineryType(machinery.getMachineryType().getMachineryType())
                .inventoryNumber(machinery.getInventoryNumber())
                .licencePlate(machinery.getLicensePlate())
                .statusCode(machinery.getCurrentStatus().getStatus())
                .build();
    }

    public static MachineryBasicInfoView toBasicInfoView(Machinery machinery) {
        return MachineryBasicInfoView.builder()
                .machineryId(machinery.getId())
                .machineryName(machinery.getMachineryName())
                .machineryType(machinery.getMachineryType().getMachineryType())
                .licensePlate(machinery.getLicensePlate())
                .build();
    }

}
