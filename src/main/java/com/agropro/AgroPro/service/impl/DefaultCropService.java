package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.CropNotFoundException;
import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.service.CropService;
import com.agropro.AgroPro.view.CropView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCropService implements CropService {

    private final CropRepository cropRepository;

    @Override
    public void validateCropExistsById(Long cropId) {
        if (!cropRepository.existsByCropId(cropId)) {
            throw new CropNotFoundException(cropId);
        }
    }

    @Override
    public List<CropView> getAllCrops() {
        return cropRepository.findAll();
    }
}
