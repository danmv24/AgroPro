package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "work_machineries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkMachinery {

    @Id
    @Column("id")
    private Long id;

    @Column("work_id")
    private Long workId;

    @Column("machinery_id")
    private Long machineryId;

}
