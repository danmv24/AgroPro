package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkEquipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEquipmentRepository extends CrudRepository<WorkEquipment, Long> {
}
