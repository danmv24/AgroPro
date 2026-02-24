package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.view.ExpenseCategoryView;

public class ExpenseCategoryMapper {

    public static ExpenseCategoryView toView(ExpenseCategory expenseCategory) {
        return ExpenseCategoryView.builder()
                .id(expenseCategory.getId())
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .build();
    }

}
