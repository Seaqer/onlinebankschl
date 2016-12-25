package ru.sbt.core.common;


/**
 * Интерфейс для проверки вводимых данных на корректность
 */
public interface Validator<T> {

    /**
     * Проверить вводимые данные
     * @param data данные для проверки
     * @return результат проверки
     */
    boolean validate(T data);
}
