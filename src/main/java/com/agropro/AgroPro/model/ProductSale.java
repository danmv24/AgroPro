package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("product_sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSale {

    @Id
    private Long id;

    @Column("product")
    private Product product;

    @Column("price")
    private BigDecimal price;

    @Column("quantity")
    private BigDecimal quantity;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("sale_date")
    private LocalDate saleDate;

}
