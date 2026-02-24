package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ExpenseCategoryNotFound extends NotFoundException {

    private final Long expenseCategoryId;

    public ExpenseCategoryNotFound(Long expenseCategoryId) {
        super("Категория затрат с id = " + expenseCategoryId + " не найдена");
        this.expenseCategoryId = expenseCategoryId;
    }
}
