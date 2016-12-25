package ru.sbt.core.userservice.entity;


import ru.sbt.core.accountservice.Account;
import ru.sbt.core.userservice.Client;

import java.util.Set;

public class InfoClient implements Client {
    private Long id;
    private Long idUser;
    private String first_name;
    private String second_name;
    private String last_name;
    private Long inn;
    private Set<Account> accounts;

    public InfoClient(Long id, Long idUser, String first_name, String second_name, String last_name, Long inn) {
        this.id = id;
        this.idUser = idUser;
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.inn = inn;
    }

    public static Client createEmpty() {
        return new InfoClient(null, null, null, null, null, null);
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
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String getFirst_name() {
        return first_name;
    }

    @Override
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String getSecond_name() {
        return second_name;
    }

    @Override
    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    @Override
    public String getLast_name() {
        return last_name;
    }

    @Override
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public Long getInn() {
        return inn;
    }

    @Override
    public void setInn(Long inn) {
        this.inn = inn;
    }

    @Override
    public Set<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "InfoClient{" +
                "first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}
