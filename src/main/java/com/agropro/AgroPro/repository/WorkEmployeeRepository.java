package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkEmployee;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEmployeeRepository extends ListCrudRepository<WorkEmployee, Long> {
}
