package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldWithCurrentCropView {

    private final Long fieldId;
    private final Integer fieldNumber;
    private final String cropName;
    private final BigDecimal area;

}
