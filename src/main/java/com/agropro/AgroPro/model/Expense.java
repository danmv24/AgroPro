package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "expenses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {

    @Id
    private Long id;

    @Column("category_id")
    private Long categoryId;

    @Column("amount")
    private BigDecimal amount;

    @Column("expense_date")
    private LocalDate expenseDate;

    @Column("description")
    private String description;

}
