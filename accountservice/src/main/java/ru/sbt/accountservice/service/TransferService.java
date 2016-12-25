package ru.sbt.accountservice.service;


import ru.sbt.core.accountservice.Transfers;

import java.math.BigDecimal;

public interface TransferService {
    /**
     * Создание корректировки
     * @param fromAccountNumber номер л.с отправителя платежа
     * @param toAccountNumber номер л.с. получателя платежа
     * @param sum сумма платежа
     * @return совершен ли платеж
     */
    boolean makeCorrection(Long fromAccountNumber, Long toAccountNumber, BigDecimal sum);

    /**
     * Обнуление корректировки
     * @param idCorrection id корректировки
     * @return отменена ли корректировка
     */
    boolean cancelCorrection(Long idCorrection);

    /**
     * Поиск корректировки по id
     *
     * @param idCorrection id корректировки
     * @return корректировка
     */
    Transfers searchTransfers(Long idCorrection);

}
