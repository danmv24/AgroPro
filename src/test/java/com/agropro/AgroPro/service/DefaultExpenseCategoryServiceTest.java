package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.response.ExpenseCategoryResponse;
import com.agropro.AgroPro.exception.ExpenseCategoryNotFoundException;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import com.agropro.AgroPro.service.impl.DefaultExpenseCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DefaultExpenseCategoryServiceTest {

    @Mock
    private ExpenseCategoryRepository categoryRepository;

    @InjectMocks
    private DefaultExpenseCategoryService categoryService;

    @Test
    void getExpenseCategoryById_shouldReturnCategory() {
        ExpenseCategory category = new ExpenseCategory(1L, "57395", "Оплата отпусков", "руб");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        ExpenseCategoryInternalData result = categoryService.getExpenseCategoryById(1L);

        assertNotNull(result);
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getExpenseCategoryById_shouldThrowWhenCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ExpenseCategoryNotFoundException.class, () -> categoryService.getExpenseCategoryById(1L));
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategories_shouldReturnAllCategories() {
        List<ExpenseCategory> categories = List.of(new ExpenseCategory(1L, "57395", "Оплата отпусков", "руб"),
                new ExpenseCategory(2L, "60085", "Оплата больничных", "руб"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<ExpenseCategoryResponse> result = categoryService.getCategories();

        assertEquals(2, result.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategories_shouldReturnEmptyList() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<ExpenseCategoryResponse> result = categoryService.getCategories();

        assertTrue(result.isEmpty());
        verify(categoryRepository).findAll();
    }

    @Test
    void getExpenseCategoriesByIds_ShouldReturnCategories() {
        Set<Long> ids = Set.of(1L, 2L);

        List<ExpenseCategory> categories = List.of(new ExpenseCategory(1L, "57395", "Оплата отпусков", "руб"),
                new ExpenseCategory(2L, "60085", "Оплата больничных", "руб"));

        when(categoryRepository.findAllById(ids)).thenReturn(categories);

        List<ExpenseCategoryInternalData> result = categoryService.getExpenseCategoriesByIds(ids);

        assertEquals(2, result.size());
        verify(categoryRepository).findAllById(ids);
    }

    @Test
    void getExpenseCategoriesByIds_ShouldReturnEmptyList() {
        Set<Long> ids = Set.of(1L, 2L);

        when(categoryRepository.findAllById(ids)).thenReturn(List.of());

        List<ExpenseCategoryInternalData> result = categoryService.getExpenseCategoriesByIds(ids);

        assertTrue(result.isEmpty());
        verify(categoryRepository).findAllById(ids);
    }


}
