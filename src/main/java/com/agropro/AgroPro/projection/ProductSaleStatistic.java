package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductSaleStatistic {

    private final Product product;

    private final BigDecimal price;

    private final BigDecimal quantity;

    private final BigDecimal totalAmount;

}
