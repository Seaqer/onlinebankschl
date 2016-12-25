package ru.sbt.core.accountservice;


import ru.sbt.core.Domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Account extends Domain {
    Long getId();

    void setId(Long id);

    Long getAccountNumber();

    void setAccountNumber(Long accountNumber);

    BigDecimal getBalance();

    void setBalance(BigDecimal balance);

    Long getAccountType();

    void setAccountType(Long accountType);

    Long getClientId();

    void setClientId(Long clientId);

    LocalDateTime getCreationDate();

    void setCreationDate(LocalDateTime creationDate);

    Long getAccountStatus();

    void setAccountStatus(Long accountStatus);
}
