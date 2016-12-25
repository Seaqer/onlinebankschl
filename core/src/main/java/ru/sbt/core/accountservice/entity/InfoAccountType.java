package ru.sbt.core.accountservice.entity;


import ru.sbt.core.accountservice.AccountType;

public class InfoAccountType implements AccountType{
    private Long id;
    private String name;

    public InfoAccountType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AccountType createEmpty() {
        return new InfoAccountType(null, null);
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
