package ru.sbt.accountservice.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.accountservice.dao.TransfersDao;
import ru.sbt.core.accountservice.entity.InfoTransfers;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.accountservice.service.AccountService;
import ru.sbt.accountservice.service.TransferService;
import ru.sbt.accountservice.utils.Locker;
import ru.sbt.core.accountservice.Account;
import ru.sbt.core.accountservice.Transfers;
import ru.sbt.core.common.Validator;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SimpleTransferService implements TransferService {
    private final TransfersDao transfersDao;
    private final AccountService accountService;
    private final Validator<BigDecimal> validator;
    private final Locker locker;
    private static final Logger LOGGER = Logger.getLogger(SimpleTransferService.class);

    @Autowired
    public SimpleTransferService(TransfersDao transfersDao, AccountService accountService, Validator<BigDecimal> validator, Locker locker) {
        this.transfersDao = transfersDao;
        this.accountService = accountService;
        this.validator = validator;
        this.locker = locker;
    }

    /**
     * Создание корректировки
     *
     * @param fromAccountNumber номер л.с отправителя платежа
     * @param toAccountNumber   номер л.с. получателя платежа
     * @param sum               сумма платежа
     * @return совершен ли платеж
     */
    @Transactional
    @Override
    public boolean makeCorrection(Long fromAccountNumber, Long toAccountNumber, BigDecimal sum) {
        validator.validate(sum);
        transferSynchronization(fromAccountNumber, toAccountNumber, sum);
        return true;
    }

    /**
     * Обнуление корректировки
     *
     * @param idCorrection id корректировки
     * @return отменена ли корректировка
     */
    @Transactional
    @Override
    public boolean cancelCorrection(Long idCorrection) {
        Transfers transfers = searchTransfers(idCorrection);
        transferSynchronization(transfers.getToAccount(), transfers.getFromAccount(), transfers.getAmount());
        transfersDao.deleteElement(transfers);
        return true;
    }

    /**
     * Поиск корректировки по id
     *
     * @param idCorrection id корректировки
     * @return корректировка
     */
    @Transactional
    @Override
    public Transfers searchTransfers(Long idCorrection) {
        Transfers transfers = InfoTransfers.createEmpty();
        transfers.setId(idCorrection);

        List<Transfers> transferses = transfersDao.getElements(transfers);
        if (transferses.size() < 1) {
            LOGGER.trace("SimpleAccountService:searchAccount корректировка не найдена, номер:" + idCorrection);
            throw new AccountServiceException("перевод не найден");
        }
        return transferses.get(0);
    }

    /**
     * Сихнронизация передачи
     * @param fromAccountNumber счет отправителя
     * @param toAccountNumber счет получателя
     * @param sum сумма перевода
     */
    private void transferSynchronization(Long fromAccountNumber, Long toAccountNumber, BigDecimal sum) {
        try {
            if (fromAccountNumber < toAccountNumber) {
                synchronized (locker.getOrRecreate(fromAccountNumber)) {
                    synchronized (locker.getOrRecreate(toAccountNumber)) {
                        makeTransfer(fromAccountNumber, toAccountNumber, sum);
                    }
                }
            } else {
                synchronized (locker.getOrRecreate(toAccountNumber)) {
                    synchronized (locker.getOrRecreate(fromAccountNumber)) {
                        makeTransfer(fromAccountNumber, toAccountNumber, sum);
                    }
                }
            }
        }finally {
            release(fromAccountNumber, toAccountNumber);
        }
    }

    /**
     * Совершить перевод
     * @param fromAccountNumber счет отправителя
     * @param toAccountNumber счет получателя
     * @param sum сумма перевода
     */
    private void makeTransfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal sum) {
        Account fromAccount = accountService.searchAccountByNumber(fromAccountNumber);
        Account toAccount = accountService.searchAccountByNumber(toAccountNumber);
        accountService.updateBalance(fromAccount, sum.negate());
        accountService.updateBalance(toAccount, sum);
        transfersDao.addElement(new InfoTransfers(null, sum, fromAccount.getId(), toAccount.getId(), null));
    }

    private void release(Long fromAccountNumber, Long toAccountNumber) {
        locker.release(fromAccountNumber);
        locker.release(toAccountNumber);
    }
}
