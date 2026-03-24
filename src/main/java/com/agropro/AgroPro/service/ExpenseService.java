package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.ExpenseForm;
import com.agropro.AgroPro.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    void createExpense(ExpenseForm expenseForm);

    void updateExpense(Long id, ExpenseForm expenseForm);

    List<ExpenseResponse> getExpenses();

}
