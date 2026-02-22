package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.MachineryStatusHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineryStatusHistoryRepository extends CrudRepository<MachineryStatusHistory, Long> {



}
