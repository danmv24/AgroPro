package com.agropro.AgroPro.enums;

public enum PaymentType {
    FIXED("Фиксированная"),
    HOURLY("Почасовая");

    private final String paymentTypeName;

    PaymentType(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }
}
