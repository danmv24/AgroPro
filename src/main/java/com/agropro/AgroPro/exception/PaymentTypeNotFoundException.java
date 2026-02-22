package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class PaymentTypeNotFoundException extends NotFoundException {

    private final String paymentType;

    public PaymentTypeNotFoundException(String paymentType) {
        super("Тип оплаты с название '" + paymentType + "' не найден");
        this.paymentType = paymentType;
    }
}
