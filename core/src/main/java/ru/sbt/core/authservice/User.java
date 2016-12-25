package ru.sbt.core.authservice;

import ru.sbt.core.Domain;

public interface User  extends Domain {
    Long getId();

    void setId(Long user_id);

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String passwd);

    Long getDelUser();

    void setDelUser(Long del_user);
}