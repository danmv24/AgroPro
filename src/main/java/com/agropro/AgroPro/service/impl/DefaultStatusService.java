package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.DefaultStatusNotConfiguredException;
import com.agropro.AgroPro.repository.StatusCodeRepository;
import com.agropro.AgroPro.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultStatusService implements StatusService {

    private final StatusCodeRepository statusCodeRepository;

    @Override
    public Long getIdleStatusCodeId() {
        return statusCodeRepository.findStatusIdByStatusCode("IDLE").orElseThrow(() ->
                new DefaultStatusNotConfiguredException("IDLE"));
    }
}
