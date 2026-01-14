package com.agropro.AgroPro.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentForm {

    private String equipmentName;

    private Long equipmentTypeId;

    private Integer inventoryNumber;

}
