package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.PositionView;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    List<PositionView> getAllPositions();

    Optional<Long> getPositionIdByPositionName(String positionName);

    Optional<String> getPositionNameByPositionId(Long positionId);

}
