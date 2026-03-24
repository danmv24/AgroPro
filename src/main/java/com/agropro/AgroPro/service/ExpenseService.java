package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.request.ExpenseUpdateRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import org.springframework.data.domain.Slice;

public interface ExpenseService {

    void createExpense(ExpenseRequest expenseRequest);

    void updateExpense(Long id, ExpenseUpdateRequest expenseForm);

    Slice<ExpenseResponse> getExpenses(int page, int size);

}
