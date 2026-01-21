package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "equipment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment {

    @Id
    @Column("equipment_id")
    private Long equipmentId;

    @Column("equipment_name")
    private String equipmentName;

    @Column("equipment_type_id")
    private Long equipmentTypeId;

    @Column("inventory_number")
    private Integer inventoryNumber;

    @Column("current_status_id")
    private Long currentStatusId;

    @Column("purchase_date")
    private LocalDate purchaseDate;

}
