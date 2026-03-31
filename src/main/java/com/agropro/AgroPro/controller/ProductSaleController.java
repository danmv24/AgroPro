package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.ProductSaleRequest;
import com.agropro.AgroPro.dto.response.ProductSaleResponse;
import com.agropro.AgroPro.service.ProductSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-sales")
public class ProductSaleController {

    private final ProductSaleService productSaleService;

    @PostMapping("/add")
    public ResponseEntity<Void> addSale(@Valid @RequestBody ProductSaleRequest saleRequest) {
        productSaleService.createSale(saleRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Slice<ProductSaleResponse> getSales(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size) {
        return productSaleService.getSales(page, size);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editSale(@PathVariable(name = "id") Long id, @Valid @RequestBody ProductSaleRequest saleRequest) {
        productSaleService.updateSale(id, saleRequest);
        return ResponseEntity.ok().build();
    }

}
