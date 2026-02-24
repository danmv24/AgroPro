package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.EquipmentStatusHistory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentStatusHistoryRepository extends ListCrudRepository<EquipmentStatusHistory, Long> {



}
