package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.ExpenseNotFoundException;
import com.agropro.AgroPro.form.ExpenseForm;
import com.agropro.AgroPro.mapper.ExpenseMapper;
import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.repository.ExpenseRepository;
import com.agropro.AgroPro.service.ExpenseCategoryService;
import com.agropro.AgroPro.service.ExpenseService;
import com.agropro.AgroPro.view.ExpenseView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultExpenseService implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryService categoryService;

    @Override
    public void createExpense(ExpenseForm expenseForm) {
        ExpenseCategory category = categoryService.getExpenseCategoryById(expenseForm.getCategoryId());

        expenseRepository.save(ExpenseMapper.toModel(expenseForm, category.getId()));
    }

    @Override
    public void updateExpense(Long id, ExpenseForm expenseForm) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));

        ExpenseCategory category = categoryService.getExpenseCategoryById(expenseForm.getCategoryId());
        expense.setCategoryId(category.getId());
        expense.setAmount(expenseForm.getAmount());
        expense.setExpenseDate(expenseForm.getExpenseDate());
        expense.setDescription(StringUtils.defaultIfBlank(expenseForm.getDescription(), "Нет описания"));

        expenseRepository.save(expense);
    }

    @Override
    public List<ExpenseView> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

        Set<Long> categoryIds = expenses.stream()
                .map(Expense::getId)
                .collect(Collectors.toSet());

        Map<Long, ExpenseCategory> categoriesById = categoryService.getExpenseCategoriesByIds(categoryIds).stream()
                .collect(Collectors.toMap(ExpenseCategory::getId, Function.identity()));

        return expenses.stream()
                .map(expense -> {
                    ExpenseCategory category = categoriesById.get(expense.getCategoryId());

                    return ExpenseMapper.toView(expense, category);
                }).toList();
    }
}
