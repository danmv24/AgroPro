package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.MaterialRequest;
import com.agropro.AgroPro.dto.request.MaterialUpdateRequest;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import com.agropro.AgroPro.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/add")
    public ResponseEntity<Void> addMaterial(@Valid @RequestBody MaterialRequest materialRequest) {
        materialService.createMaterial(materialRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Slice<MaterialResponse> getMaterials(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size) {
        return materialService.getMaterials(page, size);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editMaterial(@PathVariable Long id,
                                             @Valid @RequestBody MaterialUpdateRequest materialUpdateRequest) {
        materialService.updateMaterial(id, materialUpdateRequest);
        return ResponseEntity.ok().build();
    }

}
