package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.ProductSaleRequest;
import com.agropro.AgroPro.dto.response.ProductSaleResponse;
import org.springframework.data.domain.Slice;

public interface ProductSaleService {

    void createSale(ProductSaleRequest saleRequest);

    Slice<ProductSaleResponse> getSales(int page, int size);

    void updateSale(Long id, ProductSaleRequest saleRequest);
}
