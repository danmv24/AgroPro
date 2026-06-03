package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;
import com.agropro.AgroPro.model.ExpenseCategory;

public class ExpenseCategoryMapper {

    private ExpenseCategoryMapper() {
    }

    public static ExpenseCategoryResponse toResponse(ExpenseCategory expenseCategory) {
        return ExpenseCategoryResponse.builder()
                .id(expenseCategory.getId())
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .build();
    }

    public static ExpenseCategoryInternalData toInternalData(ExpenseCategory category) {
        return ExpenseCategoryInternalData.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .code(category.getCode())
                .unit(category.getUnit())
                .build();
    }
}
