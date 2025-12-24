package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.service.WorkRecordService;
import com.agropro.AgroPro.view.WorkRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work-record")
public class WorkRecordController {

    private final EmployeeService employeeService;

    private final WorkRecordService workRecordService;

    @GetMapping("/add")
    public String showWorkRecordForm(Model model) {
        model.addAttribute("workTimeForm", new WorkRecordForm());
        model.addAttribute("employees", employeeService.getHourlyPaidEmployees());

        return "work_record/worktime_add";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addWorkRecord(@RequestBody WorkRecordForm workTimeForm) {
        workRecordService.addWorkRecord(workTimeForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Успешно"));
    }

    @GetMapping("/list")
    public String getListWorkRecords(Model model) {
        List<WorkRecordView> workRecordViews = workRecordService.getAllWorkRecords();
        model.addAttribute("workRecords", workRecordViews);

        return "work_record/worktime_list";
    }

}
