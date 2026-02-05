package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MachineryRepository {

    Long save(Machinery machinery);

    List<MachineryView> findAll();

    List<MachineryBasicInfoView> findMachineriesWithIdleStatus();

    Set<Long> findExistingMachineriesByIds(Set<Long> machineryIds);

    Map<Long, Long> findMachineryStatusesByIds(Set<Long> machineryIds);

    List<Long> findConflictMachineryIdsByDateTime(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<MachineryBasicInfoView> findMachineriesByFieldWorkId(Long workId);
}
