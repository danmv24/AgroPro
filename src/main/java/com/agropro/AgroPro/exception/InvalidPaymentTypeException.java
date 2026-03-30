package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidPaymentTypeException extends InvalidEnumValueException {

    private final String paymentType;

    public InvalidPaymentTypeException(String paymentType) {
        super("Тип оплаты с название '" + paymentType + "' не найден");
        this.paymentType = paymentType;
    }
}
