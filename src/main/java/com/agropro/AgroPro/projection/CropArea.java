package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.CropType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CropArea {

    private final CropType cropType;

    private final BigDecimal sownArea;

    private final BigDecimal harvestedArea;

}
