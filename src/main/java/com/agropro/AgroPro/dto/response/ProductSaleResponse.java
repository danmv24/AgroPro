package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class ProductSaleResponse {

    private final Long id;

    private final Product product;

    private final BigDecimal price;

    private final BigDecimal quantity;

    private final BigDecimal totalAmount;

    private final LocalDate saleDate;

}
