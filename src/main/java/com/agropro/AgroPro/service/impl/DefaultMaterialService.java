package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.request.MaterialRequest;
import com.agropro.AgroPro.dto.request.MaterialUpdateRequest;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import com.agropro.AgroPro.exception.MaterialNotFoundException;
import com.agropro.AgroPro.mapper.MaterialMapper;
import com.agropro.AgroPro.model.Material;
import com.agropro.AgroPro.repository.MaterialRepository;
import com.agropro.AgroPro.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMaterialService implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public void createMaterial(MaterialRequest materialRequest) {
        materialRepository.save(MaterialMapper.toModel(materialRequest));
    }

    @Override
    public Slice<MaterialResponse> getMaterials(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Material> materials = materialRepository.findAll(pageable);

        return materials.map(MaterialMapper::toResponse);
    }

    @Override
    public void updateMaterial(Long id, MaterialUpdateRequest materialUpdateRequest) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new MaterialNotFoundException(id));

        material.setMaterialName(materialUpdateRequest.getMaterialName());
        material.setCurrentPrice(materialUpdateRequest.getCurrentPrice());
        materialRepository.save(material);
    }

    @Override
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id).orElseThrow(() -> new MaterialNotFoundException(id));
    }

}
