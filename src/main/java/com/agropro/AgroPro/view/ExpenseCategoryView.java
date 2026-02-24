package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExpenseCategoryView {

    private final Long id;

    private final String categoryName;

    private final String code;

}
