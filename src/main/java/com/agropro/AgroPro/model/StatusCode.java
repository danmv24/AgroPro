package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "status_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusCode {

    @Id
    @Column("status_id")
    private Long statusId;

    @Column("status_code")
    private String statusCode;

    @Column("display_name")
    private String displayName;

}
