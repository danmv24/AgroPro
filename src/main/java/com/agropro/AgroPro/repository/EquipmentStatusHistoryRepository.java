package com.agropro.AgroPro.repository;

public interface EquipmentStatusHistoryRepository {

    void save(Long equipmentId, Long statusId);

}
