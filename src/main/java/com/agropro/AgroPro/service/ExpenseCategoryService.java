package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;

import java.util.List;
import java.util.Set;

public interface ExpenseCategoryService {

    ExpenseCategoryInternalData getExpenseCategoryById(Long id);

    List<ExpenseCategoryResponse> getCategories();

    List<ExpenseCategoryInternalData> getExpenseCategoriesByIds(Set<Long> categoryIds);

}
