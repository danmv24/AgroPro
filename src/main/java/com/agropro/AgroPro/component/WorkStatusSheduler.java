package com.agropro.AgroPro.component;

import com.agropro.AgroPro.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class WorkStatusSheduler {

    private final WorkService fieldWorkService;

    @Scheduled(fixedRate = 60000)
    public void updateFieldWorkStatuses() {
        try {
            fieldWorkService.updateStatuses();
            log.info("Обновление завершено");
        } catch (Exception e) {
            log.error("Ошибка при обновлении", e);
        }
    }

}
