package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.FieldPlanting;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FieldPlantingRepository extends ListCrudRepository<FieldPlanting, Long> {

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

    @Query("""
        SELECT id, field_id, crop_type, planting_date, harvest_date FROM field_plantings
        WHERE field_id IN (:fieldIds)
        AND planting_date <= :date
        AND (harvest_date IS NULL OR harvest_date > :date)
    """)
    List<FieldPlanting> findAllByIdAndDate(@Param("fieldIds") Set<Long> fieldIds, @Param("date") LocalDate date);



}
