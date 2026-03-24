package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.EquipmentRequest;
import com.agropro.AgroPro.dto.request.EquipmentUpdateRequest;
import com.agropro.AgroPro.dto.response.EquipmentResponse;
import com.agropro.AgroPro.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping("/add")
    public ResponseEntity<Void> addMachinery(@Valid @RequestBody EquipmentRequest equipmentRequest) {
        equipmentService.createEquipment(equipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Slice<EquipmentResponse> getAllEquipment(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "15") int size) {
        return equipmentService.getAllEquipment(page, size);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentUpdateRequest equipmentUpdateRequest) {
        equipmentService.updateEquipment(id, equipmentUpdateRequest);
        return ResponseEntity.ok().build();
    }

}
