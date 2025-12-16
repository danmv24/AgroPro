package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "harvests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Harvest {

    @Id
    private Long id;

    @Column("field_id")
    private Long fieldId;

    @Column("harvest_date")
    private LocalDate harvestDate;

    @Column("quantity")
    private BigDecimal quantity;

}
