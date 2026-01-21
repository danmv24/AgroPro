package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "machinery_status_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MachineryStatusHistory {

    @Id
    private Long id;

    @Column("machinery_id")
    private Long machineryId;

    @Column("status_id")
    private Long statusId;

    @Column("changed_at")
    private LocalDateTime changedAt;

}
