package ru.sbt.core.accountservice.entity;


import ru.sbt.core.accountservice.Status;

public class InfoStatus implements Status {
    private Long id;
    private String name;

    public InfoStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static InfoStatus createEmpty() {
        return new InfoStatus(null, null);
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
