package com.agropro.AgroPro.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FieldPlantingForm {

    private Long fieldId;

    private Long cropId;

    private Integer plantingYear;

}
