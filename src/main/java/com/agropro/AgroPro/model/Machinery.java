package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.enums.StatusCode;
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
    private Long id;

    @Column("machinery_name")
    private String machineryName;

    @Column("machinery_type")
    private MachineryType machineryType;

    @Column("inventory_number")
    private Integer inventoryNumber;

    @Column("license_plate")
    private String licensePlate;

    @Column("current_status")
    private StatusCode currentStatus;

    @Column("purchase_date")
    private LocalDate purchaseDate;

}
