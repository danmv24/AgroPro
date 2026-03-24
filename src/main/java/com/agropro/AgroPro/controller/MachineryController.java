package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.MachineryRequest;
import com.agropro.AgroPro.dto.request.MachineryUpdateRequest;
import com.agropro.AgroPro.dto.response.MachineryResponse;
import com.agropro.AgroPro.service.MachineryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/machineries")
public class MachineryController {

    private final MachineryService machineryService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Void> addMachinery(@Valid @RequestBody MachineryRequest machineryRequest) {
        machineryService.createMachinery(machineryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Slice<MachineryResponse> getAllMachineries(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "15") int size) {
        return machineryService.getMachineries(page, size);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editMachinery(@PathVariable Long id, @Valid @RequestBody MachineryUpdateRequest machineryUpdateRequest) {
        machineryService.updateMachinery(id, machineryUpdateRequest);
        return ResponseEntity.ok().build();
    }

}
