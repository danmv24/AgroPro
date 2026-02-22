package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldWithCurrentCropView {

    private final Long id;
    private final Integer fieldNumber;
    private final String cropType;
    private final BigDecimal area;

}
