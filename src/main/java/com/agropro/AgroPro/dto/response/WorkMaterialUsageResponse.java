package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.MaterialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkMaterialUsageResponse {

    private final String materialName;

    private final MaterialType materialType;

    private final BigDecimal quantity;

}
