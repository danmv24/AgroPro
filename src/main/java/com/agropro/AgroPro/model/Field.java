package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "fields")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field {

    @Id
    private Long id;

    @Column("field_number")
    private Integer fieldNumber;

    @Column("area")
    private BigDecimal area;
}
