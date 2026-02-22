package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkEmployee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEmployeeRepository extends CrudRepository<WorkEmployee, Long> {
}
