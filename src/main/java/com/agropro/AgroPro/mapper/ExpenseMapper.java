package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import org.apache.commons.lang3.StringUtils;

public class ExpenseMapper {

    public static Expense toModel(ExpenseRequest expenseRequest, Long expenseCategoryId) {
        return Expense.builder()
                .categoryId(expenseCategoryId)
                .amount(expenseRequest.getAmount())
                .expenseDate(expenseRequest.getExpenseDate())
                .description(StringUtils.defaultIfBlank(expenseRequest.getDescription(), "Нет описания"))
                .build();
    }

    public static ExpenseResponse toView(Expense expense, ExpenseCategory expenseCategory) {
        return ExpenseResponse.builder()
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .description(expense.getDescription())
                .build();
    }

}
