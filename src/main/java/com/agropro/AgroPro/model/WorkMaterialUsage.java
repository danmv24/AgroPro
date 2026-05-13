package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("work_material_usage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class WorkMaterialUsage {

    @Id
    private Long id;

    @Column("work_id")
    private Long workId;

    @Column("material_id")
    private Long materialId;

    @Column("quantity")
    private BigDecimal quantity;

    @Column("price_per_unit")
    private BigDecimal pricePerUnit;

    @Column("total_cost")
    private BigDecimal totalCost;

}
