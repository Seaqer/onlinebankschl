package ru.sbt.accountservice.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.accountservice.dao.AccountDao;
import ru.sbt.core.accountservice.entity.InfoAccount;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.service.AccountService;
import ru.sbt.accountservice.service.OtherService;
import ru.sbt.accountservice.utils.Locker;
import ru.sbt.core.accountservice.Account;
import ru.sbt.core.accountservice.Status;
import ru.sbt.core.common.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.*;

@Service
public class SimpleAccountService implements AccountService {
    private final Locker locker;
    private final Validator<BigDecimal> validator;
    private final AccountDao accountDao;
    private final OtherService otherService;
    private static final Logger LOGGER = Logger.getLogger(SimpleAccountService.class);

    @Autowired
    public SimpleAccountService(AccountDao accountDao, Validator<BigDecimal> validator, OtherService otherService, Locker locker) {
        this.accountDao = accountDao;
        this.validator = validator;
        this.otherService = otherService;
        this.locker = locker;
    }

    /**
     * Создание лицевого счета
     *
     * @param account Лицевой счет
     * @return создан ли
     */
    @Transactional
    @Override
    public Account createAccount(Account account) {
        if(Objects.isNull(account.getClientId())){
            throw new AccountServiceException("идентификатор клиента не может быть равен 0");
        }
        otherService.searchStatusById(account.getAccountStatus());
        otherService.searchTypeById(account.getAccountType());
        return accountDao.addElement(account);
    }

    /**
     * Обновление лицевого счета
     *
     * @param account лицевой счет
     * @return обновлен ли
     */
    @Transactional
    @Override
    public boolean updateAccount(Account account) {
        otherService.searchStatusById(account.getAccountStatus());
        synchronized (locker.getOrRecreate(account.getAccountNumber())) {
            Account oldAccount = searchAccountByNumber(account.getAccountNumber());
            account.setId(oldAccount.getId());
            accountDao.updateElement(account);
        }
        locker.release(account.getAccountNumber());
        return true;
    }

    /**
     * Обновление статуса лицевого счета
     *
     * @param accountNumber номер счета
     * @param newStatus     новый статус
     * @return обновлен ли
     */
    @Transactional
    @Override
    public boolean updateAccountStatus(Long accountNumber, String newStatus) {
        synchronized (locker.getOrRecreate(accountNumber)) {
            Status status = otherService.searchStatusByName(newStatus);
            Account account = searchAccountByNumber(accountNumber);
            account.setAccountStatus(status.getId());
            accountDao.updateAccountStatus(account);
        }
        locker.release(accountNumber);
        return true;
    }

    /**
     * Обновить баланс счета
     *
     * @param account    номер лицевого счета
     * @param correction корректировка
     * @return обновлен ли счет
     */
    @Transactional
    @Override
    public boolean updateBalance(Account account, BigDecimal correction) {
        synchronized (locker.getOrRecreate(account.getAccountNumber())) {
            BigDecimal newBalace = account.getBalance().add(correction);
            if (correction.compareTo(ONE) < 0 && newBalace.compareTo(ZERO) < 0) {
                throw new AccountServiceException("сумма на счету не достаточна для проведения корректировки");
            }
            account.setBalance(newBalace);
            accountDao.updateBalance(account);
        }
        locker.release(account.getAccountNumber());
        return true;
    }


    /**
     * Поиск лицевого счета по номеру
     *
     * @param number номер лицевого счета
     * @return лицевой счет
     */
    @Transactional
    @Override
    public Account searchAccountByNumber(Long number) {
        Account account = InfoAccount.createEmpty();
        account.setAccountNumber(number);

        List<Account> statusList = accountDao.getElements(account);
        if (statusList.size() < 1) {
            LOGGER.trace("SimpleAccountService:searchAccount лицевой счет не найден, номер:" + number);
            throw new AccountServiceException("Лицевой счет не найден");
        }
        return statusList.get(0);
    }

    @Transactional
    @Override
    public List<Account> searchAccountByClntId(Long clntID) {
        Account account = InfoAccount.createEmpty();
        account.setClientId(clntID);

        List<Account> statusList = accountDao.getElementsByClntId(account);
        if (statusList.size() < 1) {
            LOGGER.trace("SimpleAccountService:searchAccount лицевой счет не найден, id клинета:" + clntID);
            throw new AccountServiceException("Лицевой счет не найден");
        }
        return statusList;
    }

    @Transactional
    @Override
    public boolean deleteAccount(Long AccountNumber) {
        Account account = searchAccountByNumber(AccountNumber);
        synchronized (locker.getOrRecreate(account.getAccountNumber())) {
            accountDao.deleteElement(account);
        }
        locker.release(account.getAccountNumber());
        return true;
    }
}
