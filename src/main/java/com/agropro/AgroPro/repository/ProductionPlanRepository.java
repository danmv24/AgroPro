package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.ProductionPlan;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionPlanRepository extends ListCrudRepository<ProductionPlan, Long> {



}
