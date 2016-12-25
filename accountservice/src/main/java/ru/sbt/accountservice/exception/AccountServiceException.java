package ru.sbt.accountservice.exception;


import ru.sbt.core.common.ServiceException;

public class AccountServiceException extends ServiceException {
    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(Exception exception) {
        super("ошибка сервиса счетов", exception);
    }

    public AccountServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
