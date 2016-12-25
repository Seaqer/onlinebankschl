package ru.sbt.accountservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import ru.sbt.accountservice.service.OtherService;
import ru.sbt.accountservice.utils.JmsSender;
import ru.sbt.core.accountservice.AccountType;
import ru.sbt.core.accountservice.Status;
import ru.sbt.core.common.Message;

import java.util.ArrayList;

public class OtherController {

    @Autowired
    JmsSender jmsSender;

    @Autowired
    OtherService otherService;

    private final String QUEUE = "account-in";
    private final String CREATE_STATUS = "Operation = 'CREATE_STATUS'";
    private final String DELETE_STATUS = "Operation = 'DELETE_STATUS'";
    private final String UPDATE_STATUS = "Operation = 'DELETE_STATUS'";
    private final String CREATE_ACCOUNT_TYPE = "Operation = 'CREATE_ACCOUNT_TYPE'";
    private final String DELETE_ACCOUNT_TYPE = "Operation = 'DELETE_ACCOUNT_TYPE'";
    private final String UPDATE_ACCOUNT_TYPE = "Operation = 'CREATE_ACCOUNT_TYPE'";
    private final String SEARCH_ALL_ACCOUNT_TYPE = "Operation = 'SEARCH_ALL_ACCOUNT_TYPE'";
    private final String SEARCH_ALL_STATUS = "Operation = 'SEARCH_ALL_ACCOUNT_TYPE'";

    @JmsListener(destination = QUEUE, concurrency = CREATE_STATUS)
    public void createStatus(Status status) {
        try {
            otherService.createStatus(status);
            jmsSender.sendMessage(CREATE_STATUS, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_ACCOUNT_TYPE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, concurrency = CREATE_ACCOUNT_TYPE)
    public void createAccountType(AccountType accountType) {
        try {
            otherService.createType(accountType);
            jmsSender.sendMessage(CREATE_ACCOUNT_TYPE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_ACCOUNT_TYPE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, concurrency = DELETE_ACCOUNT_TYPE)
    public void deleteAccountType(AccountType accountType) {
        try {
            otherService.deleteAccountType(accountType.getName());
            jmsSender.sendMessage(DELETE_ACCOUNT_TYPE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_ACCOUNT_TYPE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, concurrency = DELETE_STATUS)
    public void deleteStatus(Status status) {
        try {
            otherService.deleteStatus(status.getName());
            jmsSender.sendMessage(DELETE_STATUS, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(DELETE_STATUS, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, concurrency = UPDATE_STATUS)
    public void updateStatus(Message message) {
        try {
            otherService.updateStatus(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_STATUS, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_STATUS, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, concurrency = UPDATE_ACCOUNT_TYPE)
    public void updateAccountType(Message message) {
        try {
            otherService.updateType(message.getFrom(), message.getTo());
            jmsSender.sendMessage(UPDATE_ACCOUNT_TYPE, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(UPDATE_ACCOUNT_TYPE, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_ALL_STATUS)
    public void seachAllStatus() {
        try {
            jmsSender.sendList(SEARCH_ALL_STATUS, otherService.searchAllStatus());
        } catch (Exception ex) {
            jmsSender.sendList(SEARCH_ALL_STATUS, new ArrayList<Status>());
        }
    }

    @JmsListener(destination = QUEUE, selector = SEARCH_ALL_ACCOUNT_TYPE)
    public void seachAllAccountType() {
        try {
            jmsSender.sendList(SEARCH_ALL_ACCOUNT_TYPE, otherService.searchALLType());
        } catch (Exception ex) {
            jmsSender.sendList(SEARCH_ALL_ACCOUNT_TYPE, new ArrayList<AccountType>());
        }
    }
}
