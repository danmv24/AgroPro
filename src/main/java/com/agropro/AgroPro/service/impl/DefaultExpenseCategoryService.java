package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;
import com.agropro.AgroPro.exception.ExpenseCategoryNotFound;
import com.agropro.AgroPro.mapper.ExpenseCategoryMapper;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import com.agropro.AgroPro.service.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultExpenseCategoryService implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    @Override
    public ExpenseCategoryInternalData getExpenseCategoryById(Long id) {
        ExpenseCategory category = categoryRepository.findById(id).orElseThrow(() -> new ExpenseCategoryNotFound(id));

        return ExpenseCategoryMapper.toInternalData(category);
    }

    @Override
    public List<ExpenseCategoryResponse> getCategories() {
        List<ExpenseCategory> categories = categoryRepository.findAll();

        return categories.stream()
                .map(ExpenseCategoryMapper::toResponse)
                .toList();
    }

    @Override
    public List<ExpenseCategoryInternalData> getExpenseCategoriesByIds(Set<Long> categoryIds) {
        List<ExpenseCategory> categories = categoryRepository.findAllById(categoryIds);

        return categories.stream()
                .map(ExpenseCategoryMapper::toInternalData)
                .toList();
    }

}
