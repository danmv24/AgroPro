package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "equipment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment {

    @Id
    @Column("equipment_id")
    private String equipmentId;

    @Column("equipment_name")
    private String equipmentName;

    @Column("equipment_type_id")
    private Long equipmentTypeId;

    @Column("inventory_number")
    private Integer inventoryNumber;

    @Column("status_id")
    private Long statusId;

}
