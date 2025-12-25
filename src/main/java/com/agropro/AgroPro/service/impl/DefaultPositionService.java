package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.mapper.PositionMapper;
import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.service.PositionService;
import com.agropro.AgroPro.view.PositionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
