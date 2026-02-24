package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.MachineryStatusHistory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineryStatusHistoryRepository extends ListCrudRepository<MachineryStatusHistory, Long> {



}
