package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.model.FieldWork;
import com.agropro.AgroPro.view.FieldWorkBasicInfoView;
import com.agropro.AgroPro.view.FieldWorkDetail;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FieldWorkRepository {

    Long save(FieldWork fieldWork);

    void linkEmployees(Long workId, Set<Long> employeeIds);

    void linkMachineries(Long workId, Set<Long> machineryIds);

    void linkEquipment(Long workId, Set<Long> equipmentIds);

    List<FieldWorkBasicInfoView> findAll();

    Optional<FieldWorkDetail> findFieldWorkById(Long workId);

    void updateStatusById(Long workId, FieldWorkStatus status);

    boolean existByFieldWorkId(Long workId);

    Optional<FieldWorkStatus> getStatusByFieldWorkId(Long workId);
}
