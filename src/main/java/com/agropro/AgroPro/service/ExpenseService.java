package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.ExpenseForm;
import com.agropro.AgroPro.view.ExpenseView;

import java.util.List;

public interface ExpenseService {

    void createExpense(ExpenseForm expenseForm);

    void updateExpense(Long id, ExpenseForm expenseForm);

    List<ExpenseView> getExpenses();

}
