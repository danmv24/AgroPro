package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExpenseCategoryResponse {

    private final Long id;

    private final String categoryName;

    private final String code;

}
