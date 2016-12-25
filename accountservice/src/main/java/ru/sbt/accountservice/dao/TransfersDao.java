package ru.sbt.accountservice.dao;


import ru.sbt.core.accountservice.Transfers;
import ru.sbt.core.common.Dao;

import java.util.List;

public interface TransfersDao extends Dao<Transfers> {

    /**
     * Получить переводы по лицевому счету за последний месяц
     * @param accountId id лицевого счета
     * @return Список транзакций
     */
    List<Transfers> getTransfersByAccount(Long accountId);
}
