package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkEquipment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEquipmentRepository extends ListCrudRepository<WorkEquipment, Long> {
}
