package com.agropro.AgroPro.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class MachineryNotFoundException extends RuntimeException {

    private final Set<Long> missingIds;

    public MachineryNotFoundException(Set<Long> missingIds) {
        super("Не найдена техника с id: " + missingIds);
        this.missingIds = missingIds;
    }
}
