package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.FieldInternalData;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.impl.DefaultFieldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultFieldServiceTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldPlantingService fieldPlantingService;

    @InjectMocks
    private DefaultFieldService fieldService;

    @Test
    void getFields_ShouldReturnAllFields() {
        List<Field> fields = List.of(new Field(1L, 1, BigDecimal.valueOf(200)),
                new Field(2L, 2, BigDecimal.valueOf(300)));

        when(fieldRepository.findAll()).thenReturn(fields);

        List<FieldBasicInfoResponse> result = fieldService.getFields();

        assertEquals(2, result.size());
        verify(fieldRepository).findAll();
    }

    @Test
    void getFields_shouldReturnEmptyList() {
        when(fieldRepository.findAll()).thenReturn(List.of());

        List<FieldBasicInfoResponse> result = fieldService.getFields();

        assertTrue(result.isEmpty());
        verify(fieldRepository).findAll();
    }

    @Test
    void getFieldById_shouldReturnField() {
        Field field = new Field(1L, 1, BigDecimal.valueOf(200));

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));

        FieldInternalData result = fieldService.getFieldById(1L);

        assertNotNull(result);
        verify(fieldRepository).findById(1L);
    }

    @Test
    void getFieldById_shouldThrowWhenFieldNotFound() {
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FieldNotFoundException.class, () -> fieldService.getFieldById(1L));
        verify(fieldRepository).findById(1L);
    }

    @Test
    void getFieldsByIds_shouldReturnFields() {
        Set<Long> ids = Set.of(1L, 2L);
        List<Field> fields = List.of(new Field(1L, 1, BigDecimal.valueOf(200)),
                new Field(2L, 2, BigDecimal.valueOf(300)));

        when(fieldRepository.findAllById(ids)).thenReturn(fields);

        List<FieldInternalData> result = fieldService.getFieldsByIds(ids);

        assertEquals(2, result.size());
        verify(fieldRepository).findAllById(ids);
    }

    @Test
    void getFieldsByIds_shouldReturnEmptyList() {
        Set<Long> ids = Set.of(1L, 2L);

        when(fieldRepository.findAllById(ids)).thenReturn(List.of());

        List<FieldInternalData> result = fieldService.getFieldsByIds(ids);

        assertTrue(result.isEmpty());
        verify(fieldRepository).findAllById(ids);
    }

    @Test
    void getFieldsWithCropByDate_shouldReturnFieldsWithPlantings() {
        List<Field> fields = List.of(new Field(1L, 1, BigDecimal.valueOf(200)),
                new Field(2L, 2, BigDecimal.valueOf(300)));
        List<FieldPlantingInternalData> plantings = List.of(new FieldPlantingInternalData(1L, 1L, CropType.SPRING_BARLEY, LocalDate.now(), null));
        LocalDate date = LocalDate.now();

        when(fieldRepository.findAll()).thenReturn(fields);
        when(fieldPlantingService.getPlantingsByIdsAndDate(Set.of(1L, 2L), date)).thenReturn(plantings);

        List<FieldResponse> result = fieldService.getFieldsWithCropByDate(date);

        assertEquals(2, result.size());
        verify(fieldRepository).findAll();
        verify(fieldPlantingService).getPlantingsByIdsAndDate(Set.of(1L, 2L), date);
    }

    @Test
    void validateFieldExistsById_shouldThrowWhenFieldNotFound() {
        when(fieldRepository.existsById(1L)).thenReturn(false);
        assertThrows(FieldNotFoundException.class, () -> fieldService.validateFieldExistsById(1L));
        verify(fieldRepository).existsById(1L);
    }

    @Test
    void getFieldsWithCropByDate_shouldReturnEmptyList() {
        when(fieldRepository.findAll()).thenReturn(List.of());
        when(fieldPlantingService.getPlantingsByIdsAndDate(Set.of(), LocalDate.now())).thenReturn(List.of());

        List<FieldResponse> result = fieldService.getFieldsWithCropByDate(LocalDate.now());

        assertTrue(result.isEmpty());
        verify(fieldRepository).findAll();
    }

}
