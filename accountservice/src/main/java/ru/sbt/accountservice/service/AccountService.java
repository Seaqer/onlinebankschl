package ru.sbt.accountservice.service;


import ru.sbt.core.accountservice.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    /**
     * Создание лицевого счета
     * @param account Лицевой счет
     * @return создан ли
     */
    Account createAccount(Account account);

    /**
     * Обновление лицевого счета
     *
     * @param account лицевой счет
     * @return обновлен ли
     */
    boolean updateAccount(Account account);

    /**
     * Обновление статуса лицевого счета
     *
     * @param accountNumber номер счета
     * @param newStatus     новый статус
     * @return обновлен ли
     */
    boolean updateAccountStatus(Long accountNumber, String newStatus);

    /**
     * Обновить баланс счета
     *
     * @param account номер лицевого счета
     * @param correction    корректировка
     * @return обновлен ли счет
     */
    boolean updateBalance(Account account, BigDecimal correction);


    /**
     * Поиск лицевого счета по номеру
     *
     * @param number номер лицевого счета
     * @return лицевой счет
     */
    Account searchAccountByNumber(Long number);

    /**
     * Поиск лицевых счетов по id клиента
     *
     * @param clntId номер лицевого счета
     * @return лицевой счет
     */
    List<Account> searchAccountByClntId(Long clntId);


    /**
     * Удалить тип счета
     * @param AccountNumber название
     * @return удален ли
     */
    boolean deleteAccount(Long AccountNumber);
}
