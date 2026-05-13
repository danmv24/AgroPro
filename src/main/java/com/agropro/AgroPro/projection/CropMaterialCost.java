package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.enums.MaterialType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class CropMaterialCost {

    private final CropType cropType;

    private final MaterialType materialType;

    private final BigDecimal totalCost;

}
