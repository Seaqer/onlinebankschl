package ru.sbt.core.userservice;


import ru.sbt.core.Domain;
import ru.sbt.core.accountservice.Account;

import java.util.Set;

public interface Client  extends Domain {

    Long getId();

    void setId(Long id);

    Long getIdUser();

    void setIdUser(Long idUser);

    String getFirst_name();

    void setFirst_name(String first_name);

    String getSecond_name();

    void setSecond_name(String second_name);

    String getLast_name();

    void setLast_name(String last_name);

    Long getInn();

    void setInn(Long inn);

    Set<Account> getAccounts();

    void setAccounts(Set<Account> accounts);
}
