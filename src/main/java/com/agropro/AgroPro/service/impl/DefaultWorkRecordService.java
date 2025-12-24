package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.EmployeeNotFoundException;
import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.mapper.WorkRecordMapper;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.repository.WorkRecordRepository;
import com.agropro.AgroPro.service.WorkRecordService;
import com.agropro.AgroPro.view.WorkRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultWorkRecordService implements WorkRecordService {

    private final EmployeeRepository employeeRepository;
    private final WorkRecordRepository workRecordRepository;

    @Override
    public void addWorkRecord(WorkRecordForm workRecordForm) {
        if (!employeeRepository.existsByEmployeeId(workRecordForm.getEmployeeId())) {
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND, workRecordForm.getEmployeeId());
        }

        workRecordRepository.save(WorkRecordMapper.toModel(workRecordForm));
    }

    @Override
    public List<WorkRecordView> getAllWorkRecords() {
        return workRecordRepository.findAllWorkRecords();
    }

}
