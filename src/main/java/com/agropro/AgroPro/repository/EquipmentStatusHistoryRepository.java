package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.EquipmentStatusHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentStatusHistoryRepository extends CrudRepository<EquipmentStatusHistory, Long> {



}
