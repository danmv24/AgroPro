package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.MaterialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class MaterialInternalData {

    private final Long id;

    private final String materialName;

    private final MaterialType materialType;

    private final BigDecimal currentPrice;

}
