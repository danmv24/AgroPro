package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository {

    List<Position> findAll();

    Optional<Long> findPositionIdByPositionName(String positionName);

    Optional<String> findPositionNameByPositionId(Long positionId);

}
