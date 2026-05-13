package com.agropro.AgroPro.exception;

public class MaterialNotFoundException extends NotFoundException {
    public MaterialNotFoundException(Long id) {
        super("Материал с id = " + id + " не найден");
    }
}
