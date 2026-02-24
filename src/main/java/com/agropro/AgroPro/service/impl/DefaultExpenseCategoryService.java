package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.ExpenseCategoryNotFound;
import com.agropro.AgroPro.mapper.ExpenseCategoryMapper;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import com.agropro.AgroPro.service.ExpenseCategoryService;
import com.agropro.AgroPro.view.ExpenseCategoryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultExpenseCategoryService implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    @Override
    public ExpenseCategory getExpenseCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ExpenseCategoryNotFound(id));
    }

    @Override
    public List<ExpenseCategoryView> getCategories() {
        List<ExpenseCategory> categories = categoryRepository.findAll();

        return categories.stream()
                .map(ExpenseCategoryMapper::toView)
                .toList();
    }

    @Override
    public List<ExpenseCategory> getExpenseCategoriesByIds(Set<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

}
