package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.MaterialType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Material {

    @Id
    private Long id;

    @Column("material_name")
    private String materialName;

    @Column("material_type")
    private MaterialType materialType;

    @Column("current_price")
    private BigDecimal currentPrice;

}
