package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.MachineryRequest;
import com.agropro.AgroPro.dto.response.MachineryBasicInfoResponse;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.model.Machinery;

public class MachineryMapper {

    private MachineryMapper() {
    }

    public static Machinery toModel(MachineryRequest machineryRequest) {
        return Machinery.builder()
                .machineryName(machineryRequest.getMachineryName())
                .inventoryNumber(machineryRequest.getInventoryNumber())
                .machineryType(machineryRequest.getMachineryType())
                .licensePlate(machineryRequest.getLicensePlate())
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(machineryRequest.getPurchaseDate())
                .build();
    }

    public static MachineryResponse toResponse(Machinery machinery) {
        return MachineryResponse.builder()
                .id(machinery.getId())
                .machineryName(machinery.getMachineryName())
                .machineryType(machinery.getMachineryType())
                .inventoryNumber(machinery.getInventoryNumber())
                .licencePlate(machinery.getLicensePlate())
                .statusCode(machinery.getCurrentStatus())
                .build();
    }

    public static MachineryBasicInfoResponse toBasicInfoResponse(Machinery machinery) {
        return MachineryBasicInfoResponse.builder()
                .machineryId(machinery.getId())
                .inventoryNumber(machinery.getInventoryNumber())
                .machineryName(machinery.getMachineryName())
                .licensePlate(machinery.getLicensePlate())
                .build();
    }

}
