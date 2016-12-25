package ru.sbt.core.authservice;


import ru.sbt.core.Domain;


public interface Role  extends Domain {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);
}
