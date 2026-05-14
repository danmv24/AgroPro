package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.response.WorkMaterialUsageResponse;

import java.util.List;

public interface WorkMaterialUsageService {

    List<WorkMaterialUsageResponse> getUsageMaterialsByWorkId(Long workId);

}
