package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidProductException extends InvalidEnumValueException {

    private final String productName;

    public InvalidProductException(String productName) {
        super("Продукт с названием '" + productName + "' не найден");
        this.productName = productName;
    }

}
