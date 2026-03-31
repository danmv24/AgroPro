package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class ProductSaleNotFoundException extends NotFoundException {

    private final Long id;

    public ProductSaleNotFoundException(Long id) {
        super("Продажа с id = " + id + " не найдена");
        this.id = id;
    }

}
