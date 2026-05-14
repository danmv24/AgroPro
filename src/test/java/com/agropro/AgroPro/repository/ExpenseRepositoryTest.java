package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class ExpenseRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    private ExpenseCategory category;

    @BeforeEach
    void setUp() {
        category = expenseCategoryRepository.findByCode("52430")
                .orElseThrow();

        expenseRepository.saveAll(List.of(
                Expense.builder()
                        .categoryId(category.getId())
                        .amount(new BigDecimal(30000))
                        .expenseDate(LocalDate.of(2026, 4, 30))
                        .build(),
                Expense.builder()
                        .categoryId(category.getId())
                        .amount(new BigDecimal(60000))
                        .expenseDate(LocalDate.of(2026, 5, 30))
                        .build()
        ));
    }

    @Test
    void findAll_ShouldReturnExpensesWithPagination() {
        Slice<Expense> expenses = expenseRepository.findAll(PageRequest.of(0, 2));

        assertThat(expenses.getContent()).hasSize(2);
        assertThat(expenses.hasNext()).isFalse();
    }

}
