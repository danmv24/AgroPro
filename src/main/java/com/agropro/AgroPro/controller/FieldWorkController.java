package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/field-works")
public class FieldWorkController {

    private final EmployeeService employeeService;
    private final MachineryService machineryService;
    private final EquipmentService equipmentService;
    private final FieldService fieldService;
    private final WorkTypeService workTypeService;
    private final FieldWorkService fieldWorkService;

    @GetMapping("/add")
    public String showPlanForm(Model model) {
        model.addAttribute("employees", employeeService.getMechanizators());
        model.addAttribute("machineries", machineryService.getIdleMachineries());
        model.addAttribute("equipment", equipmentService.getIdleEquipment());
        model.addAttribute("fields", fieldService.getAllFields());
        model.addAttribute("workTypes", workTypeService.getAllWorkTypes());
        model.addAttribute("fieldWorkForm", new FieldWorkForm());

        return "field_works/field_work_add";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> planFieldWork(@Valid @RequestBody FieldWorkForm fieldWorkForm) {
        fieldWorkService.addFieldWork(fieldWorkForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Работа успешно запланирована"));
    }

}
