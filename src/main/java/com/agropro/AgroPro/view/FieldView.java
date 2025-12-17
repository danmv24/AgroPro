package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldView {

    private final Long fieldId;

    private final Integer fieldNumber;

}
