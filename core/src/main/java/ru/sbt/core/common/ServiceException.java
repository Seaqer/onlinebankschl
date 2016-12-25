package ru.sbt.core.common;


public class ServiceException extends RuntimeException  {
    /**
     * Конструктор приложения
     *
     * @param message сообщение
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Конструктор приложения
     *
     * @param exception исключение
     */
    public ServiceException(Exception exception) {
        this("ошибка сервиса ", exception);
    }

    /**
     * Конструктор приложения
     *
     * @param message   сообщение
     * @param exception исключение
     */
    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
