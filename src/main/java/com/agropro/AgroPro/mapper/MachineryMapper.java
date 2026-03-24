package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.MachineryForm;
import com.agropro.AgroPro.dto.response.MachineryBasicInfoResponse;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.model.Machinery;

public class MachineryMapper {

    public static Machinery toModel(MachineryForm machineryForm) {
        return Machinery.builder()
                .machineryName(machineryForm.getMachineryName())
                .inventoryNumber(machineryForm.getInventoryNumber())
                .machineryType(machineryForm.getMachineryType())
                .licensePlate(machineryForm.getLicensePlate())
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(machineryForm.getPurchaseDate())
                .build();
    }

    public static MachineryResponse toView(Machinery machinery) {
        return MachineryResponse.builder()
                .machineryName(machinery.getMachineryName())
                .machineryType(machinery.getMachineryType())
                .inventoryNumber(machinery.getInventoryNumber())
                .licencePlate(machinery.getLicensePlate())
                .statusCode(machinery.getCurrentStatus().getStatusName())
                .build();
    }

    public static MachineryBasicInfoResponse toBasicInfoView(Machinery machinery) {
        return MachineryBasicInfoResponse.builder()
                .machineryId(machinery.getId())
                .machineryName(machinery.getMachineryName())
                .machineryType(machinery.getMachineryType())
                .licensePlate(machinery.getLicensePlate())
                .build();
    }

}
