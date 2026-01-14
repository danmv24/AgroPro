package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.service.EquipmentService;
import com.agropro.AgroPro.service.EquipmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentTypeService equipmentTypeService;

    private final EquipmentService equipmentService;

    @GetMapping("/add")
    public String showAddEquipmentForm(Model model) {
        model.addAttribute("equipmentForm", new EquipmentForm());
        model.addAttribute("equipmentTypes", equipmentTypeService.getAllEquipmentTypes());

        return "equipment/equipment_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addMachinery(@RequestBody EquipmentForm equipmentForm) {
        equipmentService.addEquipment(equipmentForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Оборудование успешно добавлено"));
    }


}
