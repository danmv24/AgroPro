package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ExpenseNotFoundException extends NotFoundException {

    private final Long expenseId;

    public ExpenseNotFoundException(Long expenseId) {
        super("Затрата с id = " + expenseId + " не найдена");
        this.expenseId = expenseId;
    }
}
