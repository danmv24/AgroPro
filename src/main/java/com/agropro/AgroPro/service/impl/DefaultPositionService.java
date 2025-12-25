package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.mapper.PositionMapper;
import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.service.PositionService;
import com.agropro.AgroPro.view.PositionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPositionService implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public List<PositionView> getAllPositions() {
        List<Position> positions = positionRepository.findAll();

        return positions.stream()
                .map(PositionMapper::toView)
                .toList();
    }

    @Override
    public Optional<Long> getPositionIdByPositionName(String positionName) {
        return positionRepository.findPositionIdByPositionName(positionName);
    }

    @Override
    public Optional<String> getPositionNameByPositionId(Long positionId) {
        return positionRepository.findPositionNameByPositionId(positionId);
    }


}
