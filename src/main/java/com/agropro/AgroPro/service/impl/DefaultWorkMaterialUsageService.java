package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.response.WorkMaterialUsageResponse;
import com.agropro.AgroPro.mapper.WorkMaterialUsageMapper;
import com.agropro.AgroPro.model.Material;
import com.agropro.AgroPro.model.WorkMaterialUsage;
import com.agropro.AgroPro.repository.WorkMaterialUsageRepository;
import com.agropro.AgroPro.service.MaterialService;
import com.agropro.AgroPro.service.WorkMaterialUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultWorkMaterialUsageService implements WorkMaterialUsageService {

    private final WorkMaterialUsageRepository workMaterialUsageRepository;

    private final MaterialService materialService;

    @Override
    public List<WorkMaterialUsageResponse> getUsageMaterialsByWorkId(Long workId) {
        List<WorkMaterialUsage> materialUsages = workMaterialUsageRepository.findByWorkId(workId);

        Set<Long> materialIds = materialUsages.stream()
                .map(WorkMaterialUsage::getMaterialId)
                .collect(Collectors.toSet());

        Map<Long, Material> materialById = materialService.getMaterialsByIds(materialIds).stream()
                .collect(Collectors.toMap(
                        Material::getId,
                        Function.identity()));

        return materialUsages.stream()
                .map(materialUsage ->
                        WorkMaterialUsageMapper.toResponse(materialUsage, materialById.get(materialUsage.getMaterialId())))
                .toList();
    }
}
