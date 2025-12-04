package com.agropro.AgroPro.repository;


import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.model.WorkRecord;
import com.agropro.AgroPro.view.WorkRecordView;

import java.util.List;

public interface WorkRecordRepository {

    void save(WorkRecord workRecord);

    List<WorkRecordView> findAllWorkRecords();

}
