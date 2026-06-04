package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.response.HarvestResponse;
import com.agropro.AgroPro.exception.HarvestNotFoundException;
import com.agropro.AgroPro.exception.HarvestValidationException;
import com.agropro.AgroPro.model.Harvest;
import com.agropro.AgroPro.repository.HarvestRepository;
import com.agropro.AgroPro.service.impl.DefaultHarvestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultHarvestServiceTest {

    @Mock
    private HarvestRepository harvestRepository;

    @InjectMocks
    private DefaultHarvestService harvestService;

    @Test
    void createHarvestRecord_shouldSave() {
        BigDecimal grossHarvest = BigDecimal.valueOf(100);

        harvestService.createHarvestRecord(1L, grossHarvest);

        verify(harvestRepository).save(any(Harvest.class));
    }

    @Test
    void createHarvestRecord_shouldThrowWhenValueIsNull() {
        assertThrows(HarvestValidationException.class, () -> harvestService.createHarvestRecord(1L, null));
        verifyNoInteractions(harvestRepository);
    }

    @Test
    void createHarvestRecord_shouldThrowWhenValueIsZero() {
        assertThrows(HarvestValidationException.class, () -> harvestService.createHarvestRecord(1L, BigDecimal.ZERO));
        verifyNoInteractions(harvestRepository);
    }

    @Test
    void createHarvestRecord_shouldThrowExceptionWhenValueIsNegative() {
        assertThrows(HarvestValidationException.class, () -> harvestService.createHarvestRecord(1L, BigDecimal.valueOf(-10)));
        verifyNoInteractions(harvestRepository);
    }

    @Test
    void getHarvestByWorkId_shouldReturnHarvest() {
        Harvest harvest = new Harvest(1L, 1L, BigDecimal.valueOf(1000), LocalDateTime.now());

        when(harvestRepository.findByWorkId(1L)).thenReturn(Optional.of(harvest));

        HarvestResponse result = harvestService.getHarvestByWorkId(1L);

        assertNotNull(result);
        verify(harvestRepository).findByWorkId(1L);
    }


    @Test
    void getHarvestByWorkId_shouldThrowWhenNotFound() {
        when(harvestRepository.findByWorkId(1L)).thenReturn(Optional.empty());
        assertThrows(HarvestNotFoundException.class, () -> harvestService.getHarvestByWorkId(1L));
        verify(harvestRepository).findByWorkId(1L);
    }

}
