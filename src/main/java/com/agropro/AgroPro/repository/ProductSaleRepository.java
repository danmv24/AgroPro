package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.ProductSale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSaleRepository extends ListCrudRepository<ProductSale, Long> {

    Slice<ProductSale> findAll(Pageable pageable);

}
