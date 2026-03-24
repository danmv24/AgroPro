package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.ExpenseForm;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import org.springframework.data.domain.Slice;

public interface ExpenseService {

    void createExpense(ExpenseForm expenseForm);

    void updateExpense(Long id, ExpenseForm expenseForm);

    Slice<ExpenseResponse> getExpenses(int page, int size);

}
