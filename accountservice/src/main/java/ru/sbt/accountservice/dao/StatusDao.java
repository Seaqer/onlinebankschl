package ru.sbt.accountservice.dao;

import ru.sbt.core.accountservice.Status;
import ru.sbt.core.common.Dao;

import java.util.List;


public interface StatusDao extends Dao<Status> {

    /**
     * Получить статус счета из БД по названию
     * @param status стаутс счета
     * @return Найденные статусы
     */
    List<Status> getStatusByName(Status status);

    /**
     * Получить все статусы счета из БД
     * @return Найденные статусы
     */
    List<Status> getAllStatus();
}
