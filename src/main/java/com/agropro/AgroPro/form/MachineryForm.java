package com.agropro.AgroPro.form;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MachineryForm {

    private String machineryName;

    private Long machineryTypeId;

    private Integer inventoryNumber;

    private String licensePlate;

    private LocalDate purchaseDate;

}
