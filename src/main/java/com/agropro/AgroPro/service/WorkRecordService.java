package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.view.WorkRecordView;

import java.util.List;

public interface WorkRecordService {

    void addWorkRecord(WorkRecordForm workRecordForm);

    List<WorkRecordView> getAllWorkRecords();

}
