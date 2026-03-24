package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.MachineryForm;
import com.agropro.AgroPro.dto.request.MachineryUpdateForm;
import com.agropro.AgroPro.dto.response.MachineryBasicInfoResponse;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.enums.StatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MachineryService {

    void createMachinery(MachineryForm machineryForm);

    List<MachineryResponse> getMachineries();

    void validateMachineriesExistByIds(Set<Long> machineryIds);

//    void validateMachineryStatus(Set<Long> machineryIds);

    void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<MachineryBasicInfoResponse> getMachineriesByWorkId(Long workId);

    void changeMachineryStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateMachinery(Long id, MachineryUpdateForm machineryUpdateForm);
}
