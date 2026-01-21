package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "machineries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Machinery {

    @Id
    @Column("machinery_id")
    private Long machineryId;

    @Column("machinery_name")
    private String machineryName;

    @Column("machinery_type_id")
    private Long machineryTypeId;

    @Column("inventory_number")
    private Integer inventoryNumber;

    @Column("licence_plate")
    private String licencePlate;

    @Column("current_status_id")
    private Long currentStatusId;

    @Column("purchase_date")
    private LocalDate purchaseDate;

}
