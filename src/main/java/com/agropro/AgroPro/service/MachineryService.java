package com.agropro.AgroPro.service;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.form.MachineryUpdateForm;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MachineryService {

    void createMachinery(MachineryForm machineryForm);

    List<MachineryView> getMachineries();

    void validateMachineriesExistByIds(Set<Long> machineryIds);

//    void validateMachineryStatus(Set<Long> machineryIds);

    void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<MachineryBasicInfoView> getMachineriesByWorkId(Long workId);

    void changeMachineryStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateMachinery(Long id, MachineryUpdateForm machineryUpdateForm);
}
