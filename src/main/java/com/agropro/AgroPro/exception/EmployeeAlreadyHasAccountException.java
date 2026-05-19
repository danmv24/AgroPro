package com.agropro.AgroPro.exception;

public class EmployeeAlreadyHasAccountException extends RuntimeException {

    public EmployeeAlreadyHasAccountException() {
        super("У сотрудника уже есть аккаунт");
    }

}
