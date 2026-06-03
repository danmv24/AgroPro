package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import com.agropro.AgroPro.model.Expense;
import org.apache.commons.lang3.StringUtils;

public class ExpenseMapper {

    private ExpenseMapper() {
    }

    public static Expense toModel(ExpenseRequest expenseRequest, Long expenseCategoryId) {
        return Expense.builder()
                .categoryId(expenseCategoryId)
                .amount(expenseRequest.getAmount())
                .expenseDate(expenseRequest.getExpenseDate())
                .description(StringUtils.defaultIfBlank(expenseRequest.getDescription(), "Нет описания"))
                .build();
    }

    public static ExpenseResponse toResponse(Expense expense, ExpenseCategoryInternalData expenseCategory) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .description(expense.getDescription())
                .build();
    }

}
