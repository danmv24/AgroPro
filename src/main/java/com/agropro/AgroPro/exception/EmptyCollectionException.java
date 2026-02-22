package com.agropro.AgroPro.exception;

public class EmptyCollectionException extends RuntimeException {

    public EmptyCollectionException() {
        super("Коллекция является null или пустой");
    }
}
