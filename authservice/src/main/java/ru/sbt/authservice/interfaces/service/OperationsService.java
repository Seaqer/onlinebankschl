package ru.sbt.authservice.interfaces.service;

import ru.sbt.core.authservice.Operation;
import ru.sbt.core.authservice.entity.InfoOperation;

/**
 * Сервисный слой для работы с операциями
 */
public interface OperationsService {

    /**
     * Создание операции
     * @param operation Имя операции
     * @return создана ли операция
     */
    boolean createOperation(Operation operation);

    /**
     * Обновление операции
     * @param nameOperation имя операции
     * @param newNameOperation новое имя операции
     * @return обновлена ли операция
     */
    boolean updateOperation(String nameOperation, String newNameOperation);

    /**
     * Удаление операции
     * @param operation имя операции
     * @return удалена ли операция
     */
    boolean deleteOperation(Operation operation);

    /**
     * Поиск операции
     * @param nameOperation имя операции
     * @return Операция
     */
    InfoOperation searchOperation(String nameOperation);

    /**
     * Выдать операцию роли
     * @param nameOperation имя операции
     * @param nameRole имя роли
     * @return выдана ли операция
     */
    boolean giveRoleOperation(String nameOperation, String nameRole);

    /**
     * Забрать операцию у роли
     * @param nameOperation имя операции
     * @param nameRole имя роли
     * @return забрана ли операция
     */
    boolean removeRoleOperation(String nameOperation, String nameRole);
}
