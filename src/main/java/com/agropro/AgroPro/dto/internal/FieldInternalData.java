package com.agropro.AgroPro.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldInternalData {

    private final Long id;

    private final Integer fieldNumber;

    private final BigDecimal area;

}
