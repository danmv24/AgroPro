package com.agropro.AgroPro.form;

import lombok.*;

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

}
