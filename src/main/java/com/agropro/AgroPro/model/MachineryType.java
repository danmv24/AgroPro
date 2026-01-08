package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "machinery_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MachineryType {

    @Id
    @Column("id")
    private Long id;

    @Column("machinery_type")
    private String machineryType;
}
