package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.view.ExpenseCategoryView;

import java.util.List;
import java.util.Set;

public interface ExpenseCategoryService {

    ExpenseCategory getExpenseCategoryById(Long id);

    List<ExpenseCategoryView> getCategories();

    List<ExpenseCategory> getExpenseCategoriesByIds(Set<Long> categoryIds);

}
