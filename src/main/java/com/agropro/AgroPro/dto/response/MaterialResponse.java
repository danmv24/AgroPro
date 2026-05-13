package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.MaterialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Builder
public class MaterialResponse {

    private final Long id;

    private final String materialName;

    private final MaterialType materialType;

    private final BigDecimal currentPrice;

}
