package com.agropro.AgroPro.exception;

public class OptimalSolutionNotFoundException extends NotFoundException {
    public OptimalSolutionNotFoundException(String status) {
        super("Оптимальное решение не найдено. Текущий статус: " + status);
    }
}
