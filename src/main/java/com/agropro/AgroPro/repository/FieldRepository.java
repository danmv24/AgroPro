package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Field;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FieldRepository extends ListCrudRepository<Field, Long> {

    @Query("SELECT SUM(area) FROM fields")
    BigDecimal sumAllFieldsArea();

}
