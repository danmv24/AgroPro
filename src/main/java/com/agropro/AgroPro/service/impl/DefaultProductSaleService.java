package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.request.ProductSaleRequest;
import com.agropro.AgroPro.dto.response.ProductSaleResponse;
import com.agropro.AgroPro.exception.ProductSaleNotFoundException;
import com.agropro.AgroPro.mapper.ProductSaleMapper;
import com.agropro.AgroPro.model.ProductSale;
import com.agropro.AgroPro.repository.ProductSaleRepository;
import com.agropro.AgroPro.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DefaultProductSaleService implements ProductSaleService {

    private final ProductSaleRepository productSaleRepository;

    @Override
    public void createSale(ProductSaleRequest saleRequest) {
        BigDecimal totalAmount = saleRequest.getPrice().multiply(saleRequest.getQuantity());

        productSaleRepository.save(ProductSaleMapper.toModel(saleRequest, totalAmount));
    }

    @Override
    public Slice<ProductSaleResponse> getSales(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("saleDate").descending());
        Slice<ProductSale> products = productSaleRepository.findAll(pageable);

        return products.map(ProductSaleMapper::toResponse);
    }

    @Override
    public void updateSale(Long id, ProductSaleRequest saleRequest) {
        ProductSale productSale = productSaleRepository.findById(id).orElseThrow(() ->
                new ProductSaleNotFoundException(id));

        BigDecimal totalAmount = saleRequest.getPrice().multiply(saleRequest.getQuantity());

        productSale.setProduct(saleRequest.getProduct());
        productSale.setPrice(saleRequest.getPrice());
        productSale.setQuantity(saleRequest.getQuantity());
        productSale.setTotalAmount(totalAmount);
        productSale.setSaleDate(saleRequest.getSaleDate());

        productSaleRepository.save(productSale);
    }

}
