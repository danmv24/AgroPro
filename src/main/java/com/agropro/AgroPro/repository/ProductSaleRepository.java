package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.ProductSale;
import com.agropro.AgroPro.projection.ProductSaleStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductSaleRepository extends ListCrudRepository<ProductSale, Long> {

    Slice<ProductSale> findAll(Pageable pageable);

    @Query("""
        SELECT ps.product, SUM(ps.quantity) AS total_quantity, AVG(ps.price) AS price, SUM(ps.quantity * ps.price) AS total_amount
        FROM product_sales AS ps
        WHERE sale_date >= :startDate AND sale_date <= :endDate
        GROUP BY ps.product
    """)
    List<ProductSaleStatistic> findProductSalesSummaryBySaleDateBetween(@Param("startDate") LocalDate startDate,
                                                                        @Param("endDate") LocalDate endDate);

}
