package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.Product;
import com.agropro.AgroPro.model.ProductSale;
import com.agropro.AgroPro.projection.ProductSaleStatistic;
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

public class ProductSaleRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private ProductSaleRepository productSaleRepository;

    private ProductSale p1;

    private ProductSale p2;

    private ProductSale p3;

    @BeforeEach
    void setUp() {
        p1 = productSaleRepository.save(ProductSale.builder()
                        .product(Product.APPLE)
                        .price(new BigDecimal(100))
                        .quantity(new BigDecimal(100))
                        .totalAmount(new BigDecimal(10000))
                        .saleDate(LocalDate.of(2026, 4, 30))
                .build());
        p2 = productSaleRepository.save(ProductSale.builder()
                .product(Product.SPRING_BARLEY)
                .price(new BigDecimal(45))
                .quantity(new BigDecimal(1000))
                .totalAmount(new BigDecimal(45000))
                .saleDate(LocalDate.of(2026, 4, 30))
                .build());
        p3 = productSaleRepository.save(ProductSale.builder()
                .product(Product.APPLE)
                .price(new BigDecimal(50))
                .quantity(new BigDecimal(50))
                .totalAmount(new BigDecimal(2500))
                .saleDate(LocalDate.of(2026, 5, 12))
                .build());
    }

    @Test
    void findAll_shouldReturnAllProductSalesWithPagination() {
        Slice<ProductSale> productSales = productSaleRepository.findAll(PageRequest.of(1, 2));

        assertThat(productSales.getContent()).hasSize(1);
        assertThat(productSales.hasNext()).isFalse();
        assertThat(productSales.hasPrevious()).isTrue();
    }

    @Test
    void findProductSalesSummaryBySaleDateBetween_shouldReturnAggregatedStatistics() {
        List<ProductSaleStatistic> result =
                productSaleRepository.findProductSalesSummaryBySaleDateBetween(
                        LocalDate.of(2026, 4, 1),
                        LocalDate.of(2026, 5, 31)
                );

        assertThat(result).hasSize(2);

        ProductSaleStatistic appleStatistic = result.stream()
                .filter(r -> r.getProduct() == Product.APPLE)
                .findFirst()
                .orElseThrow();

        ProductSaleStatistic barleyStatistic = result.stream()
                .filter(r -> r.getProduct() == Product.SPRING_BARLEY)
                .findFirst()
                .orElseThrow();

        assertThat(appleStatistic.getTotalQuantity()).isEqualByComparingTo("150");
        assertThat(appleStatistic.getPrice()).isEqualByComparingTo("75");
        assertThat(appleStatistic.getTotalAmount()).isEqualByComparingTo("12500");

        assertThat(barleyStatistic.getTotalQuantity()).isEqualByComparingTo("1000");
        assertThat(barleyStatistic.getPrice()).isEqualByComparingTo("45");
        assertThat(barleyStatistic.getTotalAmount()).isEqualByComparingTo("45000");
    }

}
