package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.ExpenseCategoryInternalData;
import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.request.ExpenseUpdateRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import com.agropro.AgroPro.exception.ExpenseNotFoundException;
import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.repository.ExpenseRepository;
import com.agropro.AgroPro.service.impl.DefaultExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseCategoryService categoryService;

    @InjectMocks
    private DefaultExpenseService expenseService;

    @Test
    void createExpense_shouldSaveExpense() {
        ExpenseRequest request = mock(ExpenseRequest.class);
        ExpenseCategoryInternalData category = new ExpenseCategoryInternalData(1L, "57395", "Оплата отпусков", "руб");

        when(request.getCategoryId()).thenReturn(1L);
        when(categoryService.getExpenseCategoryById(1L)).thenReturn(category);

        expenseService.createExpense(request);

        verify(categoryService).getExpenseCategoryById(1L);
        verify(expenseRepository).save(any(Expense.class));
    }

    @Test
    void updateExpense_shouldUpdateExpense() {
        ExpenseCategoryInternalData category = new ExpenseCategoryInternalData(2L, "60085", "Оплата больничных", "руб");
        LocalDate expenseDate = LocalDate.now();
        Expense expense = new Expense(1L, 1L, BigDecimal.valueOf(500), expenseDate, "описание");
        ExpenseUpdateRequest request = mock(ExpenseUpdateRequest.class);

        when(request.getCategoryId()).thenReturn(2L);

        when(request.getExpenseDate()).thenReturn(expenseDate);
        when(request.getDescription()).thenReturn("описание");
        when(request.getAmount()).thenReturn(BigDecimal.valueOf(500));

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(categoryService.getExpenseCategoryById(2L)).thenReturn(category);

        expenseService.updateExpense(1L, request);

        assertEquals(2L, expense.getCategoryId());
        assertEquals(BigDecimal.valueOf(500), expense.getAmount());
        assertEquals(expenseDate, expense.getExpenseDate());
        assertEquals("описание", expense.getDescription());
        verify(expenseRepository).save(expense);
    }

    @Test
    void updateExpense_shouldThrowWhenExpenseNotFound() {
        ExpenseUpdateRequest request = mock(ExpenseUpdateRequest.class);

        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.updateExpense(1L, request));
        verify(expenseRepository).findById(1L);
    }

    @Test
    void getExpenses_shouldReturnAllExpenses() {
        ExpenseCategoryInternalData category = new ExpenseCategoryInternalData(1L, "57395", "Оплата отпусков", "руб");
        List<Expense> expenses = List.of(new Expense(1L, 1L, BigDecimal.valueOf(500), LocalDate.now(), "описание"),
                new Expense(2L, 1L, BigDecimal.valueOf(1900), LocalDate.now(), "описание"));

        Slice<Expense> expenseSlice = new SliceImpl<>(expenses, PageRequest.of(0, 10), false);

        when(expenseRepository.findAll(any(Pageable.class))).thenReturn(expenseSlice);
        when(categoryService.getExpenseCategoriesByIds(Set.of(1L))).thenReturn(List.of(category));

        Slice<ExpenseResponse> result = expenseService.getExpenses(0, 10);

        assertEquals(2, result.getContent().size());
        verify(expenseRepository).findAll(any(Pageable.class));
        verify(categoryService).getExpenseCategoriesByIds(Set.of(1L));

    }

    @Test
    void getExpenses_shouldReturnEmptySlice() {
        Slice<Expense> expenseSlice = new SliceImpl<>(List.of(), PageRequest.of(0, 10), false);

        when(expenseRepository.findAll(any(Pageable.class))).thenReturn(expenseSlice);
        when(categoryService.getExpenseCategoriesByIds(Set.of())).thenReturn(List.of());

        Slice<ExpenseResponse> result = expenseService.getExpenses(0, 10);

        assertTrue(result.isEmpty());
    }

    @Test
    void getExpenseById_shouldReturnExpense() {
        ExpenseCategoryInternalData category = new ExpenseCategoryInternalData(1L, "57395", "Оплата отпусков", "руб");
        Expense expense = new Expense(1L, 1L, BigDecimal.valueOf(500), LocalDate.now(), "описание");

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(categoryService.getExpenseCategoryById(expense.getCategoryId())).thenReturn(category);

        ExpenseResponse result = expenseService.getExpenseById(1L);

        assertNotNull(result);
        verify(expenseRepository).findById(1L);
    }

    @Test
    void getExpenseById_shouldThrowWhenExpenseNotFound() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.getExpenseById(1L));
        verify(expenseRepository).findById(1L);
    }

}
