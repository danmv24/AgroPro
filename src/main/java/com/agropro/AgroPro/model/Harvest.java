package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "harvests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Harvest {

    @Id
    private Long id;

    @Column("work_id")
    private Long workId;

    @Column("gross_harvest")
    private BigDecimal grossHarvest;

    @Column("created_at")
    private LocalDateTime createdAt;

}
