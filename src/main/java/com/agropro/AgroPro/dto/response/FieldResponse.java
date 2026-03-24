package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldResponse {

    private final Long id;

    private final Integer fieldNumber;

    private final String cropType;

    private final BigDecimal area;

}
