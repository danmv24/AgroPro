package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.MachineryForm;
import com.agropro.AgroPro.dto.request.MachineryUpdateForm;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.service.MachineryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/machineries")
public class MachineryController {

    private final MachineryService machineryService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Void> addMachinery(@Valid @RequestBody MachineryForm machineryForm) {
        machineryService.createMachinery(machineryForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<MachineryResponse> getAllMachineries() {
        return machineryService.getMachineries();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editMachinery(@PathVariable Long id, @Valid @RequestBody MachineryUpdateForm machineryUpdateForm) {
        machineryService.updateMachinery(id, machineryUpdateForm);
        return ResponseEntity.ok().build();
    }

}
