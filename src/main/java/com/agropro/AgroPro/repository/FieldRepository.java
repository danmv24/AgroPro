package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends CrudRepository<Field, Long> {

    List<Field> findAll();

    @Query("""
        SELECT f.id, f.field_number, f.area, fp.crop_type FROM fields AS f
        LEFT JOIN field_plantings AS fp ON f.id = fp.field_id AND EXTRACT(YEAR FROM planting_date) = :year
        ORDER BY f.field_number
    """)
    List<FieldWithCurrentCropView> findFieldsWithCropByYear(@Param("year") Integer year);

}
