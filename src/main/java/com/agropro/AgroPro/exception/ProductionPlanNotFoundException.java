package com.agropro.AgroPro.exception;

public class ProductionPlanNotFoundException extends NotFoundException {
    public ProductionPlanNotFoundException(Long id) {
        super("План с id = " + id + " не найден");
    }
}
