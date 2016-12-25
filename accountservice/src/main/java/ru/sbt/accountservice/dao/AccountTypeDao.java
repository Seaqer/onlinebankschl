package ru.sbt.accountservice.dao;


import ru.sbt.core.accountservice.AccountType;
import ru.sbt.core.common.Dao;

import java.util.List;

public interface AccountTypeDao extends Dao<AccountType> {

    /**
     * Получить тип счета из БД по названию
     * @param accountType тип счета
     * @return Найденные типы
     */
    List<AccountType> getAccountTypeByName(AccountType accountType);

    /**
     * Получить все типы счетов из БД
     * @return Найденные типы
     */
    List<AccountType> getAllAccountType();
}
