package ru.sbt.core.accountservice;


import ru.sbt.core.Domain;

public interface AccountType extends Domain{

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);
}
