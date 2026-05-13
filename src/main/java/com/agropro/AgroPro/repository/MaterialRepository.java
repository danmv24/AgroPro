package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Material;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends ListCrudRepository<Material, Long> {

    Slice<Material> findAll(Pageable pageable);

}
