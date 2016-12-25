package ru.sbt.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import ru.sbt.accountservice.service.AccountService;
import ru.sbt.accountservice.utils.JmsSender;
import ru.sbt.core.accountservice.Account;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    JmsSender jmsSender;

    @Autowired
    AccountService accountService;

    private final String QUEUE = "account-in";
    private final String CREATE_ACCOUNT = "Operation = 'CREATE_ACCOUNT'";
    private final String UPDATE_BALANCE = "Operation = 'UPDATE_BALANCE'";
    private final String SEARCH_ACCOUNTS_CLIENT = "Operation = 'SEARCH_ACCOUNTS_CLIENT'";
    private final String DELETE_ACCOUNT = "Operation = 'DELETE_ACCOUNT'";

    @JmsListener(destination = QUEUE, selector = CREATE_ACCOUNT)
    public void createAccount(Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            jmsSender.sendMessage(CREATE_ACCOUNT, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CREATE_ACCOUNT, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_ACCOUNTS_CLIENT)
    public void seachAccountClient(Long clientId) {
        try {
            List<Account> accounts = accountService.searchAccountByClntId(clientId);
            jmsSender.sendList(SEARCH_ACCOUNTS_CLIENT, accounts);
        } catch (Exception ex) {
            jmsSender.sendList(SEARCH_ACCOUNTS_CLIENT, new ArrayList<Account>());
        }
    }

    @JmsListener(destination = QUEUE, selector = DELETE_ACCOUNT)
    public void deleteAccount(Long accountNumber) {
        try {
            accountService.deleteAccount(accountNumber);
            jmsSender.sendMessage(DELETE_ACCOUNT, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_ACCOUNT, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = UPDATE_BALANCE)
    public void updateBalance(Account updateInfo) {
        try {
            Account account = accountService.searchAccountByNumber(updateInfo.getAccountNumber());
            accountService.updateBalance(account, updateInfo.getBalance());
            jmsSender.sendMessage(UPDATE_BALANCE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_BALANCE, ex.getMessage());
        }
    }
}
