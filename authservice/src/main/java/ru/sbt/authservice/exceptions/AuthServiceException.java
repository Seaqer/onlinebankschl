package ru.sbt.authservice.exceptions;


import ru.sbt.core.common.ServiceException;

/**
 * Ошибка приложения создания пользователя
 */
public class AuthServiceException extends ServiceException {


    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(Exception exception) {
        super("ошибка сервиса регистрации", exception);
    }

    public AuthServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
