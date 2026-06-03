package com.agropro.AgroPro.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ExpenseCategoryInternalData {

    private final Long id;

    private final String code;

    private final String categoryName;

    private final String unit;

}
