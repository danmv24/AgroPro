package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.ProductSaleRequest;
import com.agropro.AgroPro.dto.response.ProductSaleResponse;
import com.agropro.AgroPro.model.ProductSale;

import java.math.BigDecimal;

public class ProductSaleMapper {

    public static ProductSale toModel(ProductSaleRequest saleRequest, BigDecimal totalAmount) {
        return ProductSale.builder()
                .product(saleRequest.getProduct())
                .price(saleRequest.getPrice())
                .quantity(saleRequest.getQuantity())
                .totalAmount(totalAmount)
                .saleDate(saleRequest.getSaleDate())
                .build();
    }

    public static ProductSaleResponse toResponse(ProductSale productSale) {
        return ProductSaleResponse.builder()
                .product(productSale.getProduct())
                .price(productSale.getPrice())
                .quantity(productSale.getQuantity())
                .totalAmount(productSale.getTotalAmount())
                .saleDate(productSale.getSaleDate())
                .build();
    }

}
