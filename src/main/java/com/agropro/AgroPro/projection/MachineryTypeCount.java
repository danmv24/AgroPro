package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.MachineryType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MachineryTypeCount {

    private final MachineryType machineryType;

    private final int count;

}
