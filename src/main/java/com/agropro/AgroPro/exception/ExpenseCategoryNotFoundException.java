package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ExpenseCategoryNotFoundException extends NotFoundException {

    private final Long expenseCategoryId;

    public ExpenseCategoryNotFoundException(Long expenseCategoryId) {
        super("Категория затрат с id = " + expenseCategoryId + " не найдена");
        this.expenseCategoryId = expenseCategoryId;
    }
}
