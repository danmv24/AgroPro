package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.StatusCode;
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
    private Long id;

    @Column("equipment_name")
    private String equipmentName;

    @Column("equipment_type")
    private EquipmentType equipmentType;

    @Column("inventory_number")
    private Integer inventoryNumber;

    @Column("current_status")
    private StatusCode currentStatus;

    @Column("purchase_date")
    private LocalDate purchaseDate;

}
