package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "crops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crop {

    @Id
    private Long id;

    @Column("crop_name")
    private String cropName;

}
