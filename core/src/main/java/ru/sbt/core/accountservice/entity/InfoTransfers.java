package ru.sbt.core.accountservice.entity;


import ru.sbt.core.accountservice.Transfers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InfoTransfers implements Transfers {
    private Long id;
    private BigDecimal amount;
    private Long fromAccount;
    private Long toAccount;
    private LocalDateTime transactionDate;

    public InfoTransfers(Long id, BigDecimal amount, Long fromAccount, Long toAccount, LocalDateTime transactionDate) {
        this.id = id;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionDate = transactionDate;
    }

    public static Transfers createEmpty() {
        return new InfoTransfers(null, null, null, null, null);
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
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public Long getFromAccount() {
        return fromAccount;
    }

    @Override
    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Override
    public Long getToAccount() {
        return toAccount;
    }

    @Override
    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "InfoTransfers{" +
                "id=" + id +
                ", amount=" + amount +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
