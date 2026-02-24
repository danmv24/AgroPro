package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Field;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends ListCrudRepository<Field, Long> {

//    @Query("""
//        SELECT f.id, f.field_number, f.area, fp.crop_type FROM fields AS f
//        LEFT JOIN field_plantings AS fp ON f.id = fp.field_id AND EXTRACT(YEAR FROM planting_date) = :year
//        ORDER BY f.field_number
//    """)
//    List<FieldWithCurrentCropView> findFieldsWithCropByYear(@Param("year") Integer year);

}
