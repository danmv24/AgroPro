package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Expense;
import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class ExpenseCategoryRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private ExpenseCategory category1;

    private ExpenseCategory category2;

    private ExpenseCategory category3;

    @BeforeEach
    void setUp() {
        category1 = expenseCategoryRepository.findByCode("52430")
                .orElseThrow();

        category2 = expenseCategoryRepository.findByCode("81161")
                .orElseThrow();

        category3 = expenseCategoryRepository.findByCode("81620")
                .orElseThrow();

        expenseRepository.save(Expense.builder()
                        .categoryId(category1.getId())
                        .amount(new BigDecimal(10000))
                        .expenseDate(LocalDate.of(2026, 5, 10))
                .build());

        expenseRepository.save(Expense.builder()
                .categoryId(category2.getId())
                .amount(new BigDecimal(40000))
                .expenseDate(LocalDate.of(2026, 5, 11))
                .build());

        expenseRepository.save(Expense.builder()
                .categoryId(category3.getId())
                .amount(new BigDecimal(3500000))
                .expenseDate(LocalDate.of(2026, 1, 1))
                .build());
    }

    @Test
    void findTotalAmountByCategoryCodesAndDateRange_shouldReturnTotalAmount() {
        List<ExpenseCategoryTotalAmount> result = expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(
                List.of("81161", "81620"), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));

        assertThat(result).hasSize(2);
        assertThat(result).extracting(ExpenseCategoryTotalAmount::getCode).containsExactlyInAnyOrder("81161", "81620");

        ExpenseCategoryTotalAmount gasTotal = result.stream()
                .filter(r -> r.getCode().equals("81161"))
                .findFirst()
                .orElseThrow();

        assertThat(gasTotal.getTotalAmount()).isEqualByComparingTo(new BigDecimal("40000"));

    }

    @Test
    void findTotalAmountByCategoryCodesAndDateRange_shouldReturnZeroWhenNoExpensesExist() {
        List<ExpenseCategoryTotalAmount> result = expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(
                List.of("172151"), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));

        assertThat(result).hasSize(1);

        ExpenseCategoryTotalAmount empty = result.get(0);

        assertThat(empty.getCode()).isEqualTo("172151");
        assertThat(empty.getTotalAmount()).isEqualByComparingTo("0");
    }

    @Test
    void findTotalAmountByCategoryCodesAndDateRange_shouldIgnoreExpensesOutsideDateRange() {
        List<ExpenseCategoryTotalAmount> result = expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(
                List.of("52430"), LocalDate.of(2026, 5, 12), LocalDate.of(2026, 5, 31));

        assertThat(result).hasSize(1);

        ExpenseCategoryTotalAmount categoryTotalAmount = result.get(0);

        assertThat(categoryTotalAmount.getCode()).isEqualTo("52430");
        assertThat(categoryTotalAmount.getTotalAmount()).isEqualByComparingTo(new BigDecimal("0"));
    }

}
