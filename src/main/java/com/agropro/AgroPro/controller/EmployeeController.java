package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.form.WorkRecordForm;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeView;
import com.agropro.AgroPro.view.WorkRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionRepository positionRepository;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("positions", positionRepository.findAll());
        return "employees/employee_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addEmployee(@RequestBody EmployeeForm employeeForm) {
        employeeService.addEmployee(employeeForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Сотрудник успешно добавлен"));
    }

    @GetMapping("/list")
    public String getAllEmployees(Model model) {
        List<EmployeeView> employeeViews = employeeService.getEmployees();
        model.addAttribute("employeeView", employeeViews);
        return "employees/employee_list";
    }

    @GetMapping("/work-time/add")
    public String showWorkRecordForm(Model model) {
        model.addAttribute("workTimeForm", new WorkRecordForm());
        model.addAttribute("employees", employeeService.getHourlyEmployees());

        return "employees/worktime_add";
    }

    @PostMapping("/work-time/add")
    public ResponseEntity<Map<String, String>> addWorkRecord(@RequestBody WorkRecordForm workTimeForm) {
        employeeService.addWorkRecord(workTimeForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Успешно"));
    }

    @GetMapping("/work-time/list")
    public String getListWorkRecords(Model model) {
        List<WorkRecordView> workRecordViews = employeeService.getAllWorkRecords();
        model.addAttribute("workRecords", workRecordViews);

        return "employees/worktime_list";
    }
}
