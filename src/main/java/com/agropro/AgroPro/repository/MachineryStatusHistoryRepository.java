package com.agropro.AgroPro.repository;

public interface MachineryStatusHistoryRepository {

    void save(Long machineryId, Long statusId);

}
