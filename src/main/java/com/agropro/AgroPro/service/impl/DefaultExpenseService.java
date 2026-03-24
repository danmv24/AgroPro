package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.request.ExpenseUpdateRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import com.agropro.AgroPro.exception.ExpenseNotFoundException;
import com.agropro.AgroPro.mapper.ExpenseMapper;
import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.repository.ExpenseRepository;
import com.agropro.AgroPro.service.ExpenseCategoryService;
import com.agropro.AgroPro.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public void createExpense(ExpenseRequest expenseRequest) {
        ExpenseCategory category = categoryService.getExpenseCategoryById(expenseRequest.getCategoryId());

        expenseRepository.save(ExpenseMapper.toModel(expenseRequest, category.getId()));
    }

    @Override
    public void updateExpense(Long id, ExpenseUpdateRequest expenseForm) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));

        ExpenseCategory category = categoryService.getExpenseCategoryById(expenseForm.getCategoryId());
        expense.setCategoryId(category.getId());
        expense.setAmount(expenseForm.getAmount());
        expense.setExpenseDate(expenseForm.getExpenseDate());
        expense.setDescription(StringUtils.defaultIfBlank(expenseForm.getDescription(), "Нет описания"));

        expenseRepository.save(expense);
    }

    @Override
    public Slice<ExpenseResponse> getExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("expenseDate").descending());
        Slice<Expense> expenses = expenseRepository.findAll(pageable);

        Set<Long> categoryIds = expenses.stream()
                .map(Expense::getCategoryId)
                .collect(Collectors.toSet());

        Map<Long, ExpenseCategory> categoriesById = categoryService.getExpenseCategoriesByIds(categoryIds).stream()
                .collect(Collectors.toMap(ExpenseCategory::getId, Function.identity()));

        return expenses.map(expense -> {
            ExpenseCategory category = categoriesById.get(expense.getCategoryId());
            return ExpenseMapper.toView(expense, category);
        });
    }
}
