package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.MaterialInternalData;
import com.agropro.AgroPro.dto.request.MaterialItem;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import com.agropro.AgroPro.dto.response.WorkMaterialUsageResponse;
import com.agropro.AgroPro.model.WorkMaterialUsage;

import java.math.BigDecimal;

public class WorkMaterialUsageMapper {

    private WorkMaterialUsageMapper() {
    }

    public static WorkMaterialUsage toModel(MaterialItem materialItem, Long workId, MaterialResponse material, BigDecimal totalCost) {
        return WorkMaterialUsage.builder()
                .workId(workId)
                .materialId(material.getId())
                .quantity(materialItem.getQuantity())
                .pricePerUnit(material.getCurrentPrice())
                .materialId(material.getId())
                .totalCost(totalCost)
                .build();
    }

    public static WorkMaterialUsageResponse toResponse(WorkMaterialUsage materialUsage, MaterialInternalData material) {
        return WorkMaterialUsageResponse.builder()
                .materialName(material.getMaterialName())
                .materialType(material.getMaterialType())
                .quantity(materialUsage.getQuantity())
                .build();
    }

}
