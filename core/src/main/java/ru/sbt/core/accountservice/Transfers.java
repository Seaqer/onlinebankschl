package ru.sbt.core.accountservice;

import ru.sbt.core.Domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Transfers extends Domain {
    Long getId();

    void setId(Long id);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    Long getFromAccount();

    void setFromAccount(Long fromAccount);

    Long getToAccount();

    void setToAccount(Long toAccount);

    LocalDateTime getTransactionDate();

    void setTransactionDate(LocalDateTime transactionDate);
}
