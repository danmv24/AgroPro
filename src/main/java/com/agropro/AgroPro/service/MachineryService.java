package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.MachineryRequest;
import com.agropro.AgroPro.dto.request.MachineryUpdateRequest;
import com.agropro.AgroPro.dto.response.MachineryBasicInfoResponse;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.enums.StatusCode;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MachineryService {

    void createMachinery(MachineryRequest machineryRequest);

    Slice<MachineryResponse> getMachineries(int page, int size);

    void validateMachineriesExistByIds(Set<Long> machineryIds);

//    void validateMachineryStatus(Set<Long> machineryIds);

    void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<MachineryBasicInfoResponse> getMachineriesByWorkId(Long workId);

    void changeMachineryStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateMachinery(Long id, MachineryUpdateRequest machineryUpdateRequest);
}
