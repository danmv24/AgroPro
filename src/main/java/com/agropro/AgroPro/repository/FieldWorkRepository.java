package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.FieldWork;

import java.util.Set;

public interface FieldWorkRepository {

    Long save(FieldWork fieldWork);

    void linkEmployees(Long workId, Set<Long> employeeIds);

    void linkMachineries(Long workId, Set<Long> machineryIds);

    void linkEquipment(Long workId, Set<Long> equipmentIds);

}
