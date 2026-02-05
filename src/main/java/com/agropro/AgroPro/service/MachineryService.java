package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MachineryService {

    void addMachinery(MachineryForm machineryForm);

    List<MachineryView> getMachineries();

    List<MachineryBasicInfoView> getIdleMachineries();

    void validateMachineriesExistByIds(Set<Long> machineryIds);

    Map<Long, Long> getMachineryStatusesByIds(Set<Long> machineryIds);


    void validateMachineryStatus(Set<Long> machineryIds);

    void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<MachineryBasicInfoView> getMachineriesByFieldWorkId(Long workId);
}
