package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.ExpenseForm;
import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.view.ExpenseView;
import org.apache.commons.lang3.StringUtils;

public class ExpenseMapper {

    public static Expense toModel(ExpenseForm expenseForm, Long expenseCategoryId) {
        return Expense.builder()
                .categoryId(expenseCategoryId)
                .amount(expenseForm.getAmount())
                .expenseDate(expenseForm.getExpenseDate())
                .description(StringUtils.defaultIfBlank(expenseForm.getDescription(), "Нет описания"))
                .build();
    }

    public static ExpenseView toView(Expense expense, ExpenseCategory expenseCategory) {
        return ExpenseView.builder()
                .code(expenseCategory.getCode())
                .categoryName(expenseCategory.getCategoryName())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .description(expense.getDescription())
                .build();
    }

}
