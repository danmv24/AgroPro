package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.sql.In;

@Table(name = "field_plantings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldPlanting {

    @Id
    private Long id;

    @Column("field_id")
    private Long fieldId;

    @Column("crop_id")
    private Long cropId;

    @Column("planting_year")
    private Integer plantingYear;
}
