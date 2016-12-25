package ru.sbt.userservice.exception;

import ru.sbt.core.common.ServiceException;


public class UserServiceException extends ServiceException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Exception exception) {
        super("ошибка сервиса пользователей", exception);
    }

    public UserServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
