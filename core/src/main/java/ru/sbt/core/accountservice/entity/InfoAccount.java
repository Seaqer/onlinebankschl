package ru.sbt.core.accountservice.entity;


import ru.sbt.core.accountservice.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InfoAccount implements Account {
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private Long accountType;
    private Long clientId;
    private LocalDateTime creationDate;
    private Long accountStatus;

    public InfoAccount(Long id, Long accountNumber, BigDecimal balance, Long accountType, Long clientId, LocalDateTime creationDate, Long accountStatus) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.clientId = clientId;
        this.creationDate = creationDate;
        this.accountStatus = accountStatus;
    }

    public static Account createEmpty() {
        return new InfoAccount(null, null, null, null, null, null, null);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public Long getAccountType() {
        return accountType;
    }

    @Override
    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    @Override
    public Long getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Long getAccountStatus() {
        return accountStatus;
    }

    @Override
    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "InfoAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", clientId=" + clientId +
                ", creationDate=" + creationDate +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
