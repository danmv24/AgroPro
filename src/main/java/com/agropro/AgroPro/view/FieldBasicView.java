package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldBasicView {

    private final Long fieldId;

    private final Integer fieldNumber;

}
