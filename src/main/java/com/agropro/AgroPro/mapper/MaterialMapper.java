package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.MaterialRequest;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import com.agropro.AgroPro.model.Material;

public class MaterialMapper {

    public static Material toModel(MaterialRequest materialRequest) {
        return Material.builder()
                .materialName(materialRequest.getMaterialName())
                .materialType(materialRequest.getMaterialType())
                .currentPrice(materialRequest.getCurrentPrice())
                .build();
    }

    public static MaterialResponse toResponse(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .materialName(material.getMaterialName())
                .materialType(material.getMaterialType())
                .currentPrice(material.getCurrentPrice())
                .build();
    }

}
