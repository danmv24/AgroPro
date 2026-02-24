package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class ExpenseView {

    private final String code;

    private final String categoryName;

    private final LocalDate expenseDate;

    private final BigDecimal amount;

    private final String description;

}
