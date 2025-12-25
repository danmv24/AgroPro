package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.view.PositionView;

public class PositionMapper {

    public static PositionView toView(Position position) {
        return PositionView.builder()
                .positionId(position.getPositionId())
                .positionName(position.getPositionName())
                .build();
    }

}
