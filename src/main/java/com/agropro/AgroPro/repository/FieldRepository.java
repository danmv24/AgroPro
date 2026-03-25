package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Field;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends ListCrudRepository<Field, Long> {

}
