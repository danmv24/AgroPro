package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.MaterialItem;
import com.agropro.AgroPro.model.Material;
import com.agropro.AgroPro.model.WorkMaterialUsage;

import java.math.BigDecimal;

public class WorkMaterialUsageMapper {

    public static WorkMaterialUsage toModel(MaterialItem materialItem, Long workId, Material material, BigDecimal totalCost) {
        return WorkMaterialUsage.builder()
                .workId(workId)
                .materialId(material.getId())
                .quantity(materialItem.getQuantity())
                .pricePerUnit(material.getCurrentPrice())
                .totalCost(totalCost)
                .build();
    }

}
