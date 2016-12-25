package ru.sbt.accountservice.service;


import ru.sbt.core.accountservice.AccountType;
import ru.sbt.core.accountservice.Status;

import java.util.List;

public interface OtherService {
    /**
     * Cоздание статуса счета
     *
     * @param status статус
     * @return создан ли статус
     */
    boolean createStatus(Status status);

    /**
     * Создание типа счета
     *
     * @param accountType тип
     * @return создан ли тип
     */
    boolean createType(AccountType accountType);

    /**
     * Обновление статуса счета
     * @param nameStatus имя статуса
     * @param newNameStatus новое имя статуса
     * @return обновлен ли статуса счета
     */
    boolean updateStatus(String nameStatus, String newNameStatus) ;

    /**
     * Обновление типа счета
     * @param nameType название типа счета
     * @param newNameType новое название типа счета
     * @return обновлен ли тип счета
     */
    boolean updateType(String nameType, String newNameType);

    /**
     * Удалить статус счета
     * @param name навзание
     * @return удален ли
     */
    boolean deleteStatus(String name);

    /**
     * Удалить тип счета
     * @param name название
     * @return удален ли
     */
    boolean deleteAccountType(String name);

    /**
     * Поиск статуса счета по ID
     *
     * @param id id статуса
     * @return статус
     */
    Status searchStatusById(Long id);

    /**
     * Поиск типа счета по ID
     *
     * @param id id типа
     * @return тип
     */
    AccountType searchTypeById(Long id);

    /**
     * Поиск статуса счета по названию
     *
     * @return статус
     */
    List<Status> searchAllStatus();

    /**
     * Поиск типа счета по названию
     *
     * @return тип
     */
    List<AccountType> searchALLType();


    /**
     * Поиск типа счета по названию
     *
     * @param name название
     * @return тип
     */
    AccountType searchTypeByName(String name);

    /**
     * Поиск статуса счета по названию
     *
     * @param name название
     * @return статус
     */
    Status searchStatusByName(String name);
}
