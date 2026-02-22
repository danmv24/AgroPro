package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.FieldPlanting;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldPlantingRepository extends CrudRepository<FieldPlanting, Long> {

//    List<FieldPlanting> findFieldPlantingsByFieldId(Long fieldId);

    @Query("""
        SELECT id, field_id, crop_type, planting_date, harvest_date
        FROM field_plantings
        WHERE field_id = :fieldId
        AND harvest_date IS NULL
        ORDER BY planting_date DESC
        LIMIT 1
    """)
    Optional<FieldPlanting> findFieldPlantingByFieldId(@Param("fieldId") Long fieldId);

}
