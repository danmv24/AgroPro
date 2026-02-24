package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.form.EquipmentUpdateForm;
import com.agropro.AgroPro.service.EquipmentService;
import com.agropro.AgroPro.view.EquipmentView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping("/add")
    public ResponseEntity<Void> addMachinery(@Valid @RequestBody EquipmentForm equipmentForm) {
        equipmentService.createEquipment(equipmentForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<EquipmentView> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentUpdateForm equipmentUpdateForm) {
        equipmentService.updateEquipment(id, equipmentUpdateForm);
        return ResponseEntity.ok().build();
    }

}
