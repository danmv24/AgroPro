package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MachineryTypeView {

    private final Long id;

    private final String machineryType;

}
