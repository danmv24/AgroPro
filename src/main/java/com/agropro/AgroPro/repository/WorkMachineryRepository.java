package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkMachinery;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMachineryRepository extends ListCrudRepository<WorkMachinery, Long> {
}
