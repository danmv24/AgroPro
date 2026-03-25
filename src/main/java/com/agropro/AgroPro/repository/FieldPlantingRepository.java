package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.projection.CropArea;
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
    List<FieldPlanting> findAllByFieldIdsAndDate(@Param("fieldIds") Set<Long> fieldIds, @Param("date") LocalDate date);


    @Query("""
        SELECT fp.crop_type, SUM(f.area) AS sown_area,SUM(CASE
            WHEN fp.harvest_date IS NOT NULL AND fp.harvest_date BETWEEN :startDate AND :endDate
            THEN f.area
            ELSE 0
            END) AS harvested_area
        FROM field_plantings AS fp
        INNER JOIN fields AS f ON f.id = fp.field_id
        WHERE fp.planting_date >= :startDate AND :endDate >= fp.planting_date
        GROUP BY fp.crop_type
    """)
    List<CropArea> findSownAndHarvestedAreas(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);


}
