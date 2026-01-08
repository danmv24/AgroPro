package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "equipment_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentType {

    @Id
    @Column("id")
    private Long id;

    @Column("equipment_type")
    private String equipmentType;
}
