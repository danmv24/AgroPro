package com.agropro.AgroPro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "positions")
@Getter
@Setter
@Builder
public class Position {

    @Column("position_id")
    private Long positionId;

    @Column("position_name")
    private String positionName;

}
