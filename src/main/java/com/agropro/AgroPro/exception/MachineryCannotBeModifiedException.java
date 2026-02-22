package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class MachineryCannotBeModifiedException extends ResourceCannotBeModifiedException {

    private final Long machineryId;

    public MachineryCannotBeModifiedException(Long machineryId) {
        super("Техника с id '" + machineryId + "' не может быть редактирована");
        this.machineryId = machineryId;
    }
}
