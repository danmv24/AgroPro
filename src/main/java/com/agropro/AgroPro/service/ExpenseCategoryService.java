package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;
import com.agropro.AgroPro.model.ExpenseCategory;

import java.util.List;
import java.util.Set;

public interface ExpenseCategoryService {

    ExpenseCategory getExpenseCategoryById(Long id);

    List<ExpenseCategoryResponse> getCategories();

    List<ExpenseCategory> getExpenseCategoriesByIds(Set<Long> categoryIds);

}
