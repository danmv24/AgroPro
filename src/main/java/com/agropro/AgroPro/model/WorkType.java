package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("work_types")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkType {

    @Column("work_type_id")
    private Long workTypeId;

    @Column("work_name")
    private String workName;

}
