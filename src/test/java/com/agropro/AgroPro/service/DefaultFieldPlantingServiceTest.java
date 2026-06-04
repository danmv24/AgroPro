package com.agropro.AgroPro.service;

import com.agropro.AgroPro.controller.FieldPlantingResponse;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.impl.DefaultFieldPlantingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultFieldPlantingServiceTest {

    @Mock
    private FieldPlantingRepository plantingRepository;

    @Mock
    private FieldRepository fieldRepository;

    @InjectMocks
    private DefaultFieldPlantingService plantingService;

    @Test
    void createFieldPlanting_shouldSave() {
        when(fieldRepository.existsById(1L)).thenReturn(true);

        plantingService.createFieldPlanting(1L, CropType.SPRING_WHEAT, LocalDate.now());

        verify(fieldRepository).existsById(1L);
        verify(plantingRepository).save(any(FieldPlanting.class));
    }

    @Test
    void createFieldPlanting_shouldThrowWhenFieldNotFound() {
        when(fieldRepository.existsById(1L)).thenReturn(false);

        assertThrows(FieldNotFoundException.class, () -> plantingService.createFieldPlanting(1L, CropType.SPRING_BARLEY, LocalDate.now()));
        verify(fieldRepository).existsById(1L);
        verifyNoInteractions(plantingRepository);
    }

    @Test
    void addHarvestDate_shouldUpdate() {
        FieldPlanting planting = new FieldPlanting(1L, 1L, CropType.SPRING_BARLEY, LocalDate.now(), null);

        when(fieldRepository.existsById(1L)).thenReturn(true);
        when(plantingRepository.findFieldPlantingByFieldId(1L)).thenReturn(Optional.of(planting));

        plantingService.addHarvestDate(1L, LocalDate.now());

        assertNotNull(planting.getHarvestDate());
        verify(plantingRepository).save(planting);
    }

    @Test
    void addHarvestDate_shouldThrowWhenFieldNotFound() {
        when(fieldRepository.existsById(1L)).thenReturn(false);
        assertThrows(FieldNotFoundException.class, () -> plantingService.addHarvestDate(1L, LocalDate.now()));
        verifyNoInteractions(plantingRepository);
    }

    @Test
    void addHarvestDate_shouldThrow_whenPlantingNotFound() {
        when(fieldRepository.existsById(1L)).thenReturn(true);
        when(plantingRepository.findFieldPlantingByFieldId(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> plantingService.addHarvestDate(1L, LocalDate.now()));
        verify(plantingRepository).findFieldPlantingByFieldId(1L);
    }

    @Test
    void getPlantingsByIdsAndDate_shouldReturn() {
        List<FieldPlanting> plantings = List.of(new FieldPlanting(1L, 1L, CropType.SPRING_BARLEY, LocalDate.now(), null),
                new FieldPlanting(2L, 2L, CropType.WINTER_WHEAT, LocalDate.now(), null));

        when(plantingRepository.findAllByFieldIdsAndDate(Set.of(1L, 2L), LocalDate.now())).thenReturn(plantings);

        List<FieldPlantingInternalData> result = plantingService.getPlantingsByIdsAndDate(Set.of(1L, 2L), LocalDate.now());

        assertEquals(2, result.size());
        verify(plantingRepository).findAllByFieldIdsAndDate(Set.of(1L, 2L), LocalDate.now());
    }

    @Test
    void getPlantingsByIdsAndDate_shouldReturnEmptyList() {
        when(plantingRepository.findAllByFieldIdsAndDate(Set.of(), LocalDate.now())).thenReturn(List.of());

        List<FieldPlantingInternalData> result = plantingService.getPlantingsByIdsAndDate(Set.of(), LocalDate.now());

        assertTrue(result.isEmpty());
    }

    @Test
    void getFieldPlantingHistory_shouldThrowWhenFieldNotFound() {
        when(fieldRepository.existsById(1L)).thenReturn(false);
        assertThrows(FieldNotFoundException.class, () -> plantingService.getFieldPlantingHistory(1L, 0, 10));
        verifyNoInteractions(plantingRepository);
    }

    @Test
    void getFieldPlantingHistory_shouldReturnSlice() {
        when(fieldRepository.existsById(1L)).thenReturn(true);

        FieldPlanting planting = new FieldPlanting(1L, 1L, CropType.SPRING_BARLEY, LocalDate.now(), null);
        Slice<FieldPlanting> plantings = new SliceImpl<>(List.of(planting), PageRequest.of(0, 10), false);

        when(plantingRepository.findByFieldIdOrderByPlantingDateDesc(eq(1L), any(Pageable.class))).thenReturn(plantings);

        Slice<FieldPlantingResponse> result = plantingService.getFieldPlantingHistory(1L, 0, 10);

        assertEquals(1, result.getContent().size());
        verify(fieldRepository).existsById(1L);
        verify(plantingRepository).findByFieldIdOrderByPlantingDateDesc(eq(1L), any(Pageable.class));
    }

}
