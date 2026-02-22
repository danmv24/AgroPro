package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkMachinery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMachineryRepository extends CrudRepository<WorkMachinery, Long> {
}
