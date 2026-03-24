package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.ExpenseCategory;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {

    @Query("""
        SELECT ec.code, COALESCE(sum(e.amount), 0) AS total_amount
        FROM expense_categories AS ec
        LEFT JOIN expenses AS e
            ON e.category_id = ec.id
            AND e.expense_date >= :startDate
            AND e.expense_date <= :endDate
        WHERE ec.code IN (:codes)
        GROUP BY ec.code
    """)
    List<ExpenseCategoryTotalAmount> findTotalAmountByCategoryCodesAndDateRange(@Param("codes") List<String> codes,
                                                                                @Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate);

}
