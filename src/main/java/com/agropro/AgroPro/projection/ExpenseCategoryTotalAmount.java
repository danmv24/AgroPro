package com.agropro.AgroPro.projection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ExpenseCategoryTotalAmount {

    private final String code;

    private final BigDecimal totalAmount;

}
