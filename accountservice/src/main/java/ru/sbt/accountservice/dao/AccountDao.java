package ru.sbt.accountservice.dao;


import ru.sbt.core.accountservice.Account;
import ru.sbt.core.common.Dao;

import java.util.List;

public interface AccountDao extends Dao<Account> {
    List<Account> getElementsByClntId(Account account);
    long updateBalance(Account account);
    long updateAccountStatus(Account account);
}
