package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.CropType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

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

    @Column("crop_type")
    private CropType cropType;

    @Column("planting_date")
    private LocalDate plantingDate;

    @Column("harvest_date")
    private LocalDate harvestDate;
}
