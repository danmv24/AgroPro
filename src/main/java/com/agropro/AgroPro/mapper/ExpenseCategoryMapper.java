package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;
import com.agropro.AgroPro.model.ExpenseCategory;

public class ExpenseCategoryMapper {

    public static ExpenseCategoryResponse toView(ExpenseCategory expenseCategory) {
        return ExpenseCategoryResponse.builder()
                .id(expenseCategory.getId())
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .build();
    }

}
