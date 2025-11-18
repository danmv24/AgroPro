package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository {

    Optional<Position> findByPositionName(String positionName);

    List<Position> findAll();

    Optional<Position> findByPositionId(Long positionId);
}
